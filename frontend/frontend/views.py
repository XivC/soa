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
    ME = "http://localhost:8080"
    url = ME + request.path  # Replace with the URL of the other server

    headers = {k: str(v) for k, v in request.META.items() if k != 'HTTP_HOST'}
    params = request.GET

    response = requests.request(request.method, url, headers=headers, params=params, data=request.body)

    try:
        root = ET.fromstring(response.content)
        json_resp = json.dumps(response_processor(root), indent=4)
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


def dragons(request):
    if request.method == 'GET':
        return forward_request(request, response_processor=make_array_processor('dragons', 'Dragon'))
    else:
        return forward_request(request)


def persons(request):
    if request.method == 'GET':
        return forward_request(request, response_processor=make_array_processor('persons', 'Person'))
    else:
        return forward_request(request)
