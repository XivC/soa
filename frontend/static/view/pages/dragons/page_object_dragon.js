import {PageObject} from '../common/page_object.js'
import {dragonRepository} from "../../../data/repository/dragon_repository.js";
import {personRepository} from "../../../data/repository/person_repository.js";

export class PageObjectDragon extends PageObject {

    constructor(props, entity) {
        super(props, ['name', 'x', 'y', 'age', 'color', 'character', 'type'], entity)
    }

    onCreate() {
        super.onCreate();
    }

    onUpdateEntity(fields) {
        dragonRepository.updateDragon(this.entity.id, fields, () => this.app.popPage())
    }

    onCreateEntity(fields) {
        dragonRepository.createDragon(fields, () => this.app.popPage())
    }
}