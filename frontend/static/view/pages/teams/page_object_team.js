import {PageObject} from '../common/page_object.js'
import {teamRepository} from "../../../data/repository/team_repository.js";
import {authRepository} from "../../../data/repository/auth_repository.js";

export class PageObjectTeam extends PageObject {

    constructor(app, entity) {
        let fields = ['name', 'size', 'startCaveId']
        if (entity == null) {
            fields.push('id')
        }
        super(app, fields, entity)
    }

    onCreate() {
        super.onCreate()
    }

    onUpdateEntity(fields) {
        alert('Not implemented')
    }

    onCreateEntity(fields) {
        teamRepository.createTeam(
            fields,
            authRepository.getUserId(),
            (data, error) => {
                if (data) {
                    this.app.popPage()
                } else {
                    alert(error)
                }
            }
        )
    }

    onDeleteEntity() {
        alert('Not implemented')
    }
}