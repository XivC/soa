import xmltodict
from django.shortcuts import render
from django.http import HttpResponse
import json
import requests
import xml.etree.ElementTree as ET
import re
import frontend.config as config


def index_view(request):
    return render(request, 'index.html')


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
    url = host + request.path  # Replace with the URL of the other server

    required_headers = ['Authorization', 'Content-Type', 'Origin']
    headers = {}
    for key in required_headers:
        if key in request.headers:
            headers[key] = request.headers[key]
    params = request.GET.urlencode()

    response = requests.request(request.method, url + '?' + params, headers=headers, data=request.body, verify=False)

    try:
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
