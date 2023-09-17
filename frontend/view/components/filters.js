export function addFilter(name, filters, container, callback) {
    const filterElement = document.createElement("div");
    let elementId = `filter-${name}`
    //card.classList.add("card");
    filterElement.innerHTML = `
    <label htmlFor="${elementId}">${name}</label>
    <select id="${elementId}">
        <option value="asc">Ascending</option>
        <option value="desc">Descending</option>
    </select>`;
    container.appendChild(filterElement)
    let dropdown = document.getElementById(elementId)
    dropdown.addEventListener("change", function () {
        filters[name] = dropdown.value
        callback()
    });
    filters[name] = dropdown.value
}

export function createFiltersContainer() {
    const container = document.createElement("div");
    container.innerHTML = `<div></div>`;
    document.body.insertAdjacentElement('afterbegin', container)
    return container
}