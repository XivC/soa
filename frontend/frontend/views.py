from django.shortcuts import render
from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt
import requests
import xmltodict
import json
import requests


def index_view(request):
    return render(request, 'index.html')


def forward_request(request):
    # Extract request data
    url = "http://188.242.74.186:8080" + request.path  # Replace with the URL of the other server

    headers = {k: str(v) for k, v in request.META.items() if k != 'HTTP_HOST'}
    params = request.GET

    response = requests.request(request.method, url, headers=headers, params=params, data=request.body)

    forwarded_response = HttpResponse(response.content, status=response.status_code)
    forwarded_response['Content-Type'] = 'application/json'
    xml_dict = xmltodict.parse(forwarded_response.content)
    forwarded_response.content = json.dumps(xml_dict, indent=4)
    return forwarded_response
