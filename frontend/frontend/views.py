from django.shortcuts import render


def index_view(request):
    return render(request, 'index.html')


def redirect_to_crud(request):
    target_url = 'http://example.com'

    # Redirect all incoming requests to the target server
    return HttpResponseRedirect(target_url)
    target_url = 'http://example.com'

    # Redirect all incoming requests to the target server
    return HttpResponseRedirect(target_url)


def proxy_api_request(request, *args, **kwargs):
    # Define the URL of the target server
    target_server_url = "http://api.example.com/api-endpoint"  # Replace with your API endpoint

    try:
        # Forward the client's request to the target server
        target_url = f"{target_server_url}{request.get_full_path()}"
        headers = dict(request.headers)
        response = requests.request(request.method, target_url, params=request.GET, data=request.body, headers=headers)

        # Return the target server's response to the client, including headers
        django_response = HttpResponse(response.content, status=response.status_code,
                                       content_type=response.headers['Content-Type'])

        # Copy headers from the target server's response to the Django response
        for key, value in response.headers.items():
            django_response[key] = value

        return django_response

    except Exception as e:
        # Handle any errors that occur during the request
        return JsonResponse({'error': str(e)}, status=500)
