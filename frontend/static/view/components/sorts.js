import {Sort} from "../../data/model/sort.js";

export class SortsComponent {

    constructor(keys, callback) {
        let container = this.createSortsContainer()
        let that = this
        this.sorts = {}
        keys.forEach((key) => {
            that.addSort(key, container, callback)
        });
    }

    getSorts() {
        return Object.entries(this.sorts).map(([key, order]) => new Sort(key, order));
    }

    createSortsContainer() {
        const container = document.createElement("div");
        container.innerHTML = `<div></div>`;
        document.body.insertAdjacentElement('afterbegin', container)
        return container
    }

    addSort(name, container, callback) {
        const filterElement = document.createElement("div");
        let elementId = `filter-${name}`
        filterElement.innerHTML = `
            <label htmlFor="${elementId}">${name}</label>
            <select id="${elementId}">
                <option value="ASC">Ascending</option>
                <option value="DESC">Descending</option>
            </select>`;
        container.appendChild(filterElement)
        let dropdown = document.getElementById(elementId)
        let that = this
        dropdown.addEventListener("change", function () {
            that.sorts[name] = dropdown.value
            callback()
        });
        this.sorts[name] = dropdown.value
    }
}