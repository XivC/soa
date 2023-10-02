// Create a new XMLHttpRequest object


function request(host, path, params, headers, method, callback, onError) {
    const xhr = new XMLHttpRequest();

    let url = host + path;
    if (Object.keys(params).length !== 0) {
        url += '?' + new URLSearchParams(params).toString();
    }
    xhr.open(method, url)
    for (let key in headers) {
        xhr.setRequestHeader(key, headers[key])
    }
    console.log(url, method, params, headers)
    xhr.onload = function () {
        let data
        try {
            data = JSON.parse(xhr.responseText)
        } catch (e) {
            data = {}
        }
        console.log(data)

        if (xhr.status >= 200 && xhr.status < 300) {
            callback(data, null)
        } else {
            if (data['errors'] !== undefined) {
                onError('Operation completed with errors:\n' + data['errors'].join('\n'))
            } else {
                onError('Something went error, status ' + xhr.status)
            }
        }
    };
    xhr.onerror = function () {
        alert('Network request failed');
    };

    xhr.send()
}

export function request_crud(path, params, method, callback, headers= {}, onError = () => {}) {
    return request('/', path, params, headers, method, callback, onError)
}