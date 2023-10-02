import {Dragon} from "../model/dragon.js";
import {request_crud} from "../../api/api.js";

class DragonRepository {

    getDragon(id, callback) {
        request_crud(
            `dragons/${id}/`,
            {},
            'GET',
            (data) => callback(dragonMapper(data['dragon']), null),
            {},
            (error) => callback(null, error)
        )
    }

    listDragons(page, filters, sorts, callback) {
        const limit = 5
        if (filters.length === 1 && filters[0].key === 'color') {
            request_crud(
                'dragons/filter-by-color/',
                {
                    'offset': page * limit,
                    'limit': limit,
                    'color': filters[0].value
                },
                'GET',
                (data) => callback(data['dragons'].map(dragonMapper), null),
                {},
                (error) => callback(null, error)
            )
        } else {
            request_crud(
                'dragons/',
                {
                    'offset': page * limit,
                    'limit': limit,
                    'filter': JSON.stringify(filters),
                    'order': JSON.stringify(sorts)
                },
                'GET',
                (data) => callback(data['dragons'].map(dragonMapper), null),
                {},
                (error) => callback(null, error)
            )
        }
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
            (_) => callback(true, null),
            {},
            (error) => callback(null, error)
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
            (_) => callback(true, null),
            {},
            (error) => callback(null, error)
        )
    }

    deleteDragon(id, callback) {
        request_crud(
            `dragons/${id}/`,
            {},
            'DELETE',
            (_) => callback(true),
            {},
            (error) => (error) => callback(null, error)
        )
    }

    killDragon(id, passportID, callback) {
        request_crud(
            `dragons/${id}/kill/`,
            {},
            'POST',
            (_) => callback(true),
            {'Authorization': passportID },
            (error) => callback(null, error)
        )
    }

    countByType(type, callback) {
        request_crud(
            `dragons/count-by-type/`,
            {
                'type': type
            },
            'GET',
            (data) => callback(data['sum']),
            {},
            (error) => callback(null, error)
        )
    }

    sumAge(callback) {
        request_crud(
            `dragons/sum-age/`,
            {},
            'GET',
            (data) => callback(data['sum']),
            {},
            (error) => callback(null, error)
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
