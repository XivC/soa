import {Dragon} from "../model/dragon.js";
import {request_crud} from "../../api/api.js";

class DragonRepository {

    getDragon(id, callback) {
        request_crud(
            `dragons/${id}/`,
            {},
            'GET',
            (data) => callback(dragonMapper(data['dragon']))
        )
    }

    listDragons(page, filters, sorts, callback) {
        const limit = 5
        request_crud(
            'dragons/',
            {
                'offset': page * limit,
                'limit': limit,
                'filter': JSON.stringify(filters),
                'order': JSON.stringify(sorts)
            },
            'GET',
            (data) => callback(data['dragons'].map(dragonMapper))
        )
    }

    createDragon(fields, callback) {
        request_crud(
            'dragons/',
            {
                'dragon': JSON.stringify({
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

    updateDragon(id, fields, callback) {
        request_crud(
            `dragons/${id}/`,
            {
                'dragon': JSON.stringify({
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

    deleteDragon(id, callback) {
        request_crud(
            `dragons/${id}/`,
            {},
            'DELETE',
            (_) => callback(true)
        )
    }

    killDragon(id, passportID, callback) {
        request_crud(
            `dragons/${id}/kill/`,
            {},
            'POST',
            (_) => callback(true),
            {'Authorization': passportID }
        )
    }
}

let dragonMapper = (item) => new Dragon(
    item['id'],
    item['name'],
    item['coordinates']['x'],
    item['coordinates']['y'],
    `${item['creationDate'][0]}:${item['creationDate'][1]}:${item['creationDate'][2]}`,

    item['age'],
    item['color'],
    item['character'],
    item['type'],
    item['killerId']
)

export let dragonRepository = new DragonRepository()
