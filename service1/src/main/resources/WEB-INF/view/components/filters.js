import {Filter} from '../../data/model/filter.js'

export class FiltersComponent {

    constructor(keys, callback) {
        let container = this.createFiltersContainer()
        let that = this
        this.filters = {}
        keys.forEach((key) => {
            that.addFilter(key, container, callback)
        });
    }

    getFilters() {
        return Object.entries(this.filters)
            .map(([key, value]) => new Filter(key, value))
            .filter((filter) => filter.value !== "")
    }

    createFiltersContainer() {
        const container = document.createElement("div");
        container.innerHTML = `<div></div>`;
        document.body.insertAdjacentElement('afterbegin', container)
        return container
    }

    addFilter(name, container, callback) {
        const sortElement = document.createElement("div");
        let elementId = `sort-${name}`
        sortElement.innerHTML = `
            <label htmlFor="${elementId}">${name}</label>
            <input id="${elementId}" type="text">`;
        container.appendChild(sortElement)
        let input = document.getElementById(elementId)
        let that = this
        input.addEventListener("input", (e) => {
            that.filters[name] = input.value
            callback()
        });
        this.filters[name] = input.value
    }
}