import {PageObject} from '../common/page_object.js'
import {dragonRepository} from "../../../data/repository/dragon_repository.js";
import {authRepository} from "../../../data/repository/auth_repository.js";

export class PageObjectDragon extends PageObject {

    constructor(props, entity) {
        super(props, ['name', 'x', 'y', 'age', 'color', 'character', 'type'], entity)
    }

    onCreate() {
        super.onCreate()
        document.body.insertAdjacentHTML('afterbegin', `<button id="kill-button">Kill</button>`)
        document.getElementById("kill-button").onclick = () => {
            dragonRepository.killDragon(this.entity.id, authRepository.getUserId(), () => this.app.popPage())
        }
    }

    onUpdateEntity(fields) {
        dragonRepository.updateDragon(this.entity.id, fields, (data, error) => {
            if (data) {
                this.app.popPage()
            } else {
                alert(error)
            }
        })
    }

    onCreateEntity(fields) {
        dragonRepository.createDragon(fields, (data, error) => {
            if (data) {
                this.app.popPage()
            } else {
                alert(error)
            }
        })
    }

    onDeleteEntity() {
        dragonRepository.deleteDragon(this.entity.id, (data, error) => {
            if (data) {
                this.app.popPage()
            } else {
                alert(error)
            }
        })
    }
}