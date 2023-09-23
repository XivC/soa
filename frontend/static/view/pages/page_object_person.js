import {PageObject} from './page_object.js'
import {PersonRepository} from "../../data/repository/person_repository.js";

export class PageObjectPerson extends PageObject {

    constructor(props, entity) {
        super(props, ['name', 'x', 'y', 'age', 'color', 'character', 'type'], entity);
        this.personRepository = new PersonRepository()
    }

    onCreate() {
        super.onCreate();
    }

    onUpdateEntity(fields, callback) {
        this.personRepository.updatePerson(this.entity.id, fields, callback)
    }

    onCreateEntity(fields, callback) {
        this.personRepository.createPerson(fields, callback)
    }
}