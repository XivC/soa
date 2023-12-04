import xmltodict
from django.shortcuts import render
from django.http import HttpResponse
import json
import requests
import xml.etree.ElementTree as ET
import re
import frontend.config as config

SOAP_REQUEST_TEMPLATE = None


def get_request_template():
    global SOAP_REQUEST_TEMPLATE

    if not SOAP_REQUEST_TEMPLATE:
        with open('request_template.xml') as f:
            SOAP_REQUEST_TEMPLATE = f.read()

    return SOAP_REQUEST_TEMPLATE


def id_from_path(path):
    candidates = path.split('/')[::-1]
    for candidate in candidates:
        if candidate:
            return candidate


def dict_to_xml(dct):
    content = ''
    for key, value in dct.items():
        if value in {'undefined', 'null'}:
            continue
        if isinstance(value, dict):
            text = dict_to_xml(value)
        else:
            text = value
        item = f'<{key}>{text}</{key}>'
        content += item
    return content


def json_to_xml(json_string):

    repr = json.loads(json_string)
    return dict_to_xml(repr)


def make_list_dragons_request(request):
    req_template = get_request_template()
    operation = 'getDragonsRequest'
    content = ''
    for key, value in request.GET.items():
        content += f'<{key}>{value}</{key}>'

    return req_template.format(operation=operation, content=content)


def make_filter_by_color_request(request):
    req_template = get_request_template()
    operation = 'filterByColorRequest'
    content = ''
    for key, value in request.GET.items():
        content += f'<{key}>{value}</{key}>'

    return req_template.format(operation=operation, content=content)


def make_count_by_type_request(request):
    req_template = get_request_template()
    operation = 'countByTypeRequest'
    content = ''
    for key, value in request.GET.items():
        content += f'<{key}>{value}</{key}>'

    return req_template.format(operation=operation, content=content)


def make_get_dragon_request(request):
    req_template = get_request_template()
    operation = 'getDragonRequest'
    content = f'<id>{id_from_path(request.path)}</id>'

    return req_template.format(operation=operation, content=content)


def make_delete_dragon_request(request):
    req_template = get_request_template()
    operation = 'deleteDragonRequest'
    content = f'<id>{id_from_path(request.path)}</id>'

    return req_template.format(operation=operation, content=content)


def make_create_dragon_request(request):
    req_template = get_request_template()
    operation = 'createDragonRequest'
    content = json_to_xml(request.GET['dragon'])

    return req_template.format(operation=operation, content=content)


def make_sum_age_request(request):
    req_template = get_request_template()
    operation = 'sumAgeRequest'
    content = ''

    return req_template.format(operation=operation, content=content)


def make_update_dragon_request(request):
    req_template = get_request_template()
    operation = 'updateDragonRequest'
    content = f'<id>{id_from_path(request.path)}</id>'
    content += json_to_xml(request.GET['dragon'])

    return req_template.format(operation=operation, content=content)


def make_get_person_request(request):
    req_template = get_request_template()
    operation = 'getPersonRequest'
    content = f'<passportID>{id_from_path(request.path)}</passportID>'

    return req_template.format(operation=operation, content=content)


def make_delete_person_request(request):
    req_template = get_request_template()
    operation = 'deletePersonRequest'
    content = f'<passportID>{id_from_path(request.path)}</passportID>'

    return req_template.format(operation=operation, content=content)


def make_update_person_request(request):
    req_template = get_request_template()
    operation = 'updatePersonRequest'
    content = f'<passportID>{id_from_path(request.path)}</passportID>'
    content += json_to_xml(request.GET['person'])

    return req_template.format(operation=operation, content=content)


def make_create_person_request(request):
    req_template = get_request_template()
    operation = 'createPersonRequest'
    content = json_to_xml(request.GET['person'])

    return req_template.format(operation=operation, content=content)

def index_view(request):
    return render(request, 'index.html')


def make_rest_response(response, expected_code):

    mapping = {
        'ListOfDragonsResponse': (True, make_array_processor('dragons', 'dragonResponse')),
        'dragonResponse': (True, make_item_processor('dragon', 'dragonResponse')),
        'ErrorResponse': (False, make_array_processor('errors', 'Error')),
        'SuccessResponse': (True, make_array_processor('messages', 'Message')),
        'sumResponse': (True, make_single_tag_processor('sum', 'sum')),
        'personResponse': (True, make_item_processor('person', 'personResponse'))

    }

    root = ET.fromstring(response.text)
    content = root.findall('{http://schemas.xmlsoap.org/soap/envelope/}Body')[0]

    for child in content:
        if child.tag in mapping:
            success, processor = mapping[child.tag]
            processed = processor(child)

            response = HttpResponse(json.dumps(processed), headers={'content-type': 'application/json'})
            if success:
                response.status_code = expected_code
            else:
                response.status_code = 400
            return response

    return HttpResponse(status=500)


def forward_soap_request(request, url):
    operations = [
        ('GET', r"dragons\/[0-9]*\/", make_get_dragon_request, 200),
        ('PUT', r'dragons\/[0-9]*\/', make_update_dragon_request, 200),
        ('DELETE', r'dragons\/[0-9]*\/', make_delete_dragon_request, 204),
        ('GET', r'dragons/filter-by-color\/', make_filter_by_color_request, 200),
        ('GET', r'dragons/count-by-type\/', make_count_by_type_request, 200),
        ('GET', r'dragons/sum-age\/', make_sum_age_request, 200),
        ('GET', r'dragons\/', make_list_dragons_request, 200),
        ('POST', r'dragons\/', make_create_dragon_request, 201),
        ('GET', r'persons\/', make_get_person_request, 200),
        ('POST', r'persons\/', make_create_person_request, 201),
        ('PUT', r'persons\/', make_update_person_request, 200),
        ('DELETE', r'persons\/', make_delete_person_request, 204),

    ]

    headers = make_headers(request)

    method = None
    path = None
    request_maker = None
    success_code = None

    for method, path, request_maker, success_code in operations:
        if re.findall(path, request.path) and request.method == method:
            request_maker = request_maker
            break

    assert request_maker

    body = request_maker(request)

    headers = {
        'Content-Type': 'text/xml'
    }

    response = requests.post(url, headers=headers, data=body, verify=False)

    rest_response = make_rest_response(response, success_code)

    return rest_response


def make_headers(request):
    required_headers = ['Authorization', 'Content-Type', 'Origin']
    headers = {}
    for key in required_headers:
        if key in request.headers:
            headers[key] = request.headers[key]

    return headers


def forward_request(request, response_processor=lambda root: {}):
    urls = [
        (r"dragons\/[0-9]*\/kill", config.KILLER_URL),
        (r"teams\/", config.KILLER_URL),
        (r".*", config.REST_URL),
    ]
    host = ''
    for key, value in urls:
        if re.findall(key, request.path):
            host = value
            break

    if host == config.REST_URL:
        return forward_soap_request(request, config.REST_URL)

    url = host + request.path  # Replace with the URL of the other server

    params = request.GET.urlencode()
    headers = make_headers(request)
    response = requests.request(request.method, url + '?' + params, headers=headers, data=request.body, verify=False)

    try:
        if response.status_code == 204:
            body = {}
        else:
            root = ET.fromstring(response.content)
            if 200 <= response.status_code < 300:
                body = response_processor(root)
            else:
                body = {'errors': [i.text for i in root.findall('Error')]}
        json_resp = json.dumps(body, indent=4)
    except:
        json_resp = response.content
    forwarded_response = HttpResponse(json_resp, status=response.status_code)
    forwarded_response['Content-Type'] = 'application/json'
    return forwarded_response


def make_array_processor(array_name, element_name):
    def processor(root):
        items = []
        for item in root.findall(element_name):
            xml_string = ET.tostring(item, encoding='unicode')
            json_item = xmltodict.parse(xml_string)
            items.append(list(json_item.values())[0])
        return {array_name: items}
    return processor


def make_item_processor(item_name, element_name):
    def processor(root):
        xml_string = ET.tostring(root, encoding='unicode')
        json_item = xmltodict.parse(xml_string)
        return {item_name: json_item[element_name]}
    return processor


def make_single_tag_processor(tag_name, element_name):
    def processor(root):
        found = None
        for item in root.findall(element_name):
            xml_string = ET.tostring(item, encoding='unicode')
            json_item = xmltodict.parse(xml_string)
            found = list(json_item.values())[0]
        return {tag_name: found}
    return processor

def make_sum_processor():
    def processor(root):
        xml_string = ET.tostring(root, encoding='unicode')
        json_item = xmltodict.parse(xml_string)
        return {'sum': json_item['Sum']['sum']}
    return processor


def dragons(request):
    if request.method == 'GET':
        urls = [
            (r"dragons\/[0-9]*\/", make_item_processor('dragon', 'Dragon')),
            (r"dragons\/count-by-type", make_sum_processor()),
            (r"dragons\/sum-age", make_sum_processor()),
            (r".*", make_array_processor('dragons', 'Dragon'))
        ]
        for key, value in urls:
            if re.findall(key, request.path):
                return forward_request(request, response_processor=value)
    else:
        return forward_request(request)


def persons(request):
    if request.method == 'GET':
        return forward_request(request, response_processor=make_item_processor('person', 'Person'))
    else:
        return forward_request(request)


def teams(request):
    return forward_request(request)
