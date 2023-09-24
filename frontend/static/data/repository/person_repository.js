import {Person} from "../model/person.js";
import {request_crud} from "../../api/api.js";

export class PersonRepository {

    listPersons(page, filters, sorts, callback) {
        const limit = 5
        request_crud(
            'persons/',
            {
                'offset': page * limit,
                'limit': limit,
                'filter': JSON.stringify(filters),
                'order': JSON.stringify(sorts)
            },
            'GET',
            (data) => callback(data['persons'].map((item) => new Person(
                item['id'],
                item['name'],
                item['coordinates']['x'],
                item['coordinates']['y'],
                `${item['creationDate'][0]}:${item['creationDate'][1]}:${item['creationDate'][2]}`,
                item['age'],
                item['color'],
                item['character'],
                item['type'],
                null
            )))
        )
    }

    createPerson(fields, callback) {
        request_crud(
            'persons/',
            {
                'person': JSON.stringify({
                    'name': fields['name'],
                    'coordinates': {
                        'x': fields['x'],
                        'y': fields['y']
                    },
                    'age': fields['age'],
                    'color': fields['color'],
                    'character': fields['character'],
                    'type': fields['type']
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
                    'coordinates': {
                        'x': fields['x'],
                        'y': fields['y']
                    },
                    'age': fields['age'],
                    'color': fields['color'],
                    'character': fields['character'],
                    'type': fields['type']
                })
            },
            'PUT',
            (_) => callback(true)
        )
    }
}