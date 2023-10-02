import {request_crud} from "../../api/api.js";

class TeamRepository {

    createTeam(fields, passportID, callback) {
        request_crud(
            `teams/create/${fields['id']}/${fields['name']}/${fields['size']}/${fields['startCaveId']}/`,
            {},
            'POST',
            (_) => callback(true, null),
            {'Authorization': passportID },
            (error) => callback(null, error)
        )
    }
}

export let teamRepository = new TeamRepository()
