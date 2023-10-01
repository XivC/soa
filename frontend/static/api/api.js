// Create a new XMLHttpRequest object


function request(host, path, params, method, callback) {
    const xhr = new XMLHttpRequest();

// Define the URL you want to make the request to
    let url = host + path;
    //if (method === 'GET') {
    if (Object.keys(params).length !== 0) {
        url += '?' + new URLSearchParams(params).toString();
    }
   // }
    console.log(url, method, params)

    xhr.open(method, url)

// Configure the request (GET request in this case)

// Set up a callback function to handle the response
    xhr.onload = function () {
        if (xhr.status >= 200 && xhr.status < 300) {
            const data = JSON.parse(xhr.responseText)
            console.log(data)
            callback(data)
        } else {
            // Handle errors here
            console.error('HTTP request failed with status ' + xhr.status);
        }
    };

// Handle network errors
    xhr.onerror = function () {
        console.error('Network request failed');
    };

    // if (method === 'PUT' || method === 'POST') {
    //     xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    //     console.log("body " + JSON.stringify(params))
    //     xhr.send(JSON.stringify(params));
    // } else {
    //     xhr.send();
    // }
    xhr.send()
}

export function request_crud(path, params, method, callback) {
    return request('http://localhost:8000/api/', path, params, method, callback)
}
