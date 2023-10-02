import {PageObject} from '../common/page_object.js'
import {personRepository} from "../../../data/repository/person_repository.js";
import {authRepository} from "../../../data/repository/auth_repository.js";
import {PageCollectionDragons} from "../dragons/page_collection_dragons.js";
import {PageObjectTeam} from "../teams/page_object_team.js";

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
        document.body.insertAdjacentHTML(
            'afterbegin', `<button id="create-team">Create team</button>`
        )
        document.getElementById("create-team").onclick = () => {
            this.app.pushPage(new PageObjectTeam(this.app, null))
        }
    }

    onUpdateEntity(fields) {
        personRepository.updatePerson(this.entity.passportID, fields, (entity, error) => {
            if (entity) {
                this.app.pushPage(new PageCollectionDragons(this))
            } else {
                alert(error)
            }
        })
    }

    onCreateEntity(fields) {
        personRepository.createPerson(fields, (entity, error) => {
            if (entity) {
                this.entity = entity
                authRepository.setUserId(entity.passportID)
                this.app.pushPage(new PageCollectionDragons(this))
            } else {
                alert(error)
            }
        })
    }

    onDeleteEntity() {
        personRepository.deletePerson(this.entity.passportID, (data, error) => {
            if (data) {
                authRepository.setUserId('')
                this.app.popPage()
            } else {
                alert(error)
            }
        })
    }
}