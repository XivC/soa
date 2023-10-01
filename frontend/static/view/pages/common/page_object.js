import {Page} from "./page.js";


export class PageObject extends Page {

    constructor(app, fieldNames, entity) {
        super(app)
        this.fieldNames = fieldNames
        this.entity = entity
    }

    onCreate() {
        super.onCreate()
        document.body.innerHTML = `<div class="" id="fieldsContainer"></div>
            <button id="save-button">${this.entity == null ? 'Create' : 'Save changes'}</button>
            <button id="back-button">Back</button>
            <div class="loading" id="loadingIndicator" style="display: none;">
                <div class="spinner"></div>
            </div>`;
        document.getElementById("back-button").onclick = () => this.app.popPage()
        document.getElementById("save-button").onclick = () => {
            let fields = {}
            this.fieldNames.forEach((name) => {
                let field = document.getElementById(`field-${name}`)
                fields[name] = field.value
            })
            document.getElementById("loadingIndicator").style.display = 'block';
            if (this.entity == null) {
                this.onCreateEntity(fields)
            } else {
                this.onUpdateEntity(fields)
            }
        }
        this.fieldNames.forEach((name) => this.addField(name))
    }

    addField(name) {
        let container = document.getElementById("fieldsContainer")
        const field = document.createElement("div");
        let elementId = `field-${name}`
        let value = this.entity == null ? '' : this.entity[name]
        field.innerHTML = `
            <label htmlFor="${elementId}">${name}</label>
            <input id="${elementId}" type="text" value="${value}">`;
        container.appendChild(field)
    }

    onCreateEntity(fields) {}

    onUpdateEntity(fields) {}
}
