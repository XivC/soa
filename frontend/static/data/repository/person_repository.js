import {Person} from "../model/person.js";
import {request_crud} from "../../api/api.js";

class PersonRepository {

    getPerson(passportId, callback) {
        request_crud(
            'persons/' + passportId,
            {},
            'GET',
            (data) => {
                let item = data['person']
                let person = new Person(
                    item['name'],
                    item['height'],
                    item['weight'],
                    item['passport-id'],
                    item['nationality']
                )
                callback(person)
            }
        )
    }

    createPerson(fields, callback) {
        request_crud(
            'persons/',
            {
                'person': JSON.stringify({
                    'name': fields['name'],
                    'height': fields['height'],
                    'weight': fields['weight'],
                    'passportId': fields['passportId'],
                    'nationality': fields['nationality']
                })
            },
            'POST',
            (_) => callback(true)
        )
    }

    updatePerson(id, fields, callback) {
        request_crud(
            'persons/' + id,
            {
                'person': JSON.stringify({
                    'name': fields['name'],
                    'height': fields['height'],
                    'weight': fields['weight'],
                    'nationality': fields['nationality']
                })
            },
            'PUT',
            (_) => callback(true)
        )
    }
}

export let personRepository = new PersonRepository()
