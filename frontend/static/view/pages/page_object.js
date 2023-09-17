import {Page} from "./page.js";
import {DragonRepository} from "../../data/repository/dragon_repository.js";


export class PageObject extends Page {

    constructor(app, fieldNames, entity) {
        super(app)
        this.fieldNames = fieldNames
        this.entity = entity
    }

    onCreate() {
        super.onCreate()
        document.body.innerHTML = '<div class="" id="fieldsContainer"></div>\n' +
            '<button id="save-button">Save</button>' +
            '<button id="back-button">Back</button>' +
            '<div class="loading" id="loadingIndicator" style="display: none;">\n' +
            '    <div class="spinner"></div>\n' +
            '</div>';
        document.getElementById("back-button").onclick = () => this.app.popPage()
        document.getElementById("save-button").onclick = () => {
            let fields = {}
            this.fieldNames.forEach((name) => {
                let field = document.getElementById(`field-${name}`)
                fields[name] = field.value
            })
            document.getElementById("loadingIndicator").style.display = 'block';
            if (this.entity == null) {
                this.onCreateEntity(fields, () => this.app.popPage())
            } else {
                this.onUpdateEntity(fields, () => this.app.popPage())
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

    onCreateEntity(fields, callback) {}

    onUpdateEntity(fields, callback) {}
}

export class PageObjectDragon extends PageObject {

    constructor(props, entity) {
        super(props, ['name', 'x', 'y', 'age', 'color', 'character', 'type'], entity);
        this.dragonRepository = new DragonRepository()
    }

    onCreate() {
        super.onCreate();
    }

    onUpdateEntity(fields, callback) {
        this.dragonRepository.updateDragon(this.entity.id, fields, callback)
    }

    onCreateEntity(fields, callback) {
        this.dragonRepository.createDragon(fields, callback)
    }
}