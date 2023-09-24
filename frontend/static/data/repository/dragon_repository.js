import {Dragon} from "../model/dragon.js";
import {request_crud} from "../../api/api.js";

export class DragonRepository {

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
            (data) => callback(data['dragons'].map((item) => new Dragon(
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
            'dragons/' + id,
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
}