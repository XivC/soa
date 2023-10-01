import xmltodict
from django.shortcuts import render
from django.http import HttpResponse
import json
import requests
import xml.etree.ElementTree as ET


def index_view(request):
    return render(request, 'index.html')


def forward_request(request, response_processor=lambda root: {}):
    # Extract request data
    DYBOV = "http://188.242.74.186:8080"
    ME = "http://localhost:8080/rest"
    url = ME + request.path  # Replace with the URL of the other server

    headers = {k: str(v) for k, v in request.META.items() if k != 'HTTP_HOST'}
    params = request.GET.urlencode()

    response = requests.request(request.method, url + '?' + params, headers=headers, data=request.body)

    try:
        root = ET.fromstring(response.content)
        if 200 <= response.status_code < 300:
            body = response_processor(root)
        else:
            body = {'errors:': [i.text for i in root.findall('Error')]}
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


def dragons(request):
    if request.method == 'GET':
        return forward_request(request, response_processor=make_array_processor('dragons', 'Dragon'))
    else:
        return forward_request(request)


def persons(request):
    if request.method == 'GET':
        return forward_request(request, response_processor=make_item_processor('person', 'Person'))
    else:
        return forward_request(request)
