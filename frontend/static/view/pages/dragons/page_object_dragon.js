import {PageObject} from '../common/page_object.js'
import {DragonRepository} from "../../../data/repository/dragon_repository.js";

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