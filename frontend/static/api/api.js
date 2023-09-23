// Create a new XMLHttpRequest object


function xmlToJson(xml) {
    // Process child nodes
    if (xml.hasChildNodes()) {
        const obj = {};
        for (let i = 0; i < xml.childNodes.length; i++) {
            const item = xml.childNodes.item(i);
            const nodeName = item.nodeName;
            if (typeof obj[nodeName] === "undefined") {
                obj[nodeName] = xmlToJson(item);
            } else {
                if (typeof obj[nodeName].push === "undefined") {
                    const oldObj = obj[nodeName];
                    obj[nodeName] = [];
                    obj[nodeName].push(oldObj);
                }
                obj[nodeName].push(xmlToJson(item));
            }
        }
        return obj;
    } else {
        if (xml.nodeValue == null) return "";
        return xml.nodeValue.trim();
    }
}


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
            // The request was successful; parse the XML response
            const xmlText = xhr.responseText.replace(/^\s+/gm, '');
            // Use DOMParser to parse the XML into a Document object
            const parser = new DOMParser();
            const xmlDoc = parser.parseFromString(xmlText, 'application/xml');
            const data = xmlToJson(xmlDoc)
            console.log(xmlDoc.childNodes.item(0).childNodes)
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
    return request('http://127.0.0.1:8000/api/', path, params, method, callback)
}
