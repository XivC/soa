import {PageObject} from '../common/page_object.js'
import {personRepository} from "../../../data/repository/person_repository.js";
import {authRepository} from "../../../data/repository/auth_repository.js";
import {PageCollectionDragons} from "../dragons/page_collection_dragons.js";

export class PageObjectPerson extends PageObject {

    constructor(app, entity) {
        let fields = ['name', 'height', 'weight', 'nationality']
        if (entity == null) {
            fields.push('passportID')
        }
        super(app, fields, entity);
    }

    onCreate() {
        super.onCreate()
        if (this.entity != null) {
            authRepository.setUserId(this.entity.passportID)
        }
    }

    onUpdateEntity(fields) {
        personRepository.updatePerson(this.entity.passportID, fields, () => {
            this.app.pushPage(new PageCollectionDragons(this))
        })
    }

    onCreateEntity(fields) {
        personRepository.createPerson(fields, (entity) => {
            this.entity = entity
            authRepository.setUserId(entity.passportID)
            this.app.pushPage(new PageCollectionDragons(this))
        })
    }

    onDeleteEntity() {
        personRepository.deletePerson(this.entity.passportID, () => {
            authRepository.setUserId('')
            this.app.popPage()
        })
    }
}