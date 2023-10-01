import {PageObject} from '../common/page_object.js'
import {personRepository} from "../../../data/repository/person_repository.js";
import {authRepository} from "../../../data/repository/auth_repository.js";
import {PageCollectionDragons} from "../dragons/page_collection_dragons.js";

export class PageObjectPerson extends PageObject {

    constructor(props, entity) {
        let fields = ['name', 'height', 'weight', 'nationality']
        if (entity == null) {
            fields.push('passportID')
        }
        super(props, fields, entity);
    }

    onCreate() {
        super.onCreate()
        const queryString = window.location.search
        const urlParams = new URLSearchParams(queryString)
        if (this.entity == null && urlParams.has('passport-id')) {
            const passportID = urlParams.get('passport-id')
            personRepository.getPerson(passportID, (entity) => {
                this.entity = entity
                this.onDestroy()
                this.onCreate()
            })
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
}