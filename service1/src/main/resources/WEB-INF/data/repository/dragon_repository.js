import {Dragon} from "../model/dragon.js";
import {request_crud} from "../../api/api.js";

export class DragonRepository {

    listDragons(page, filters, sorts, callback) {
        const limit = 5
        request_crud(
            'dragons/',
            {
                'page': page * limit,
                'limit': limit,
                'filter': JSON.stringify(filters),
                'sort': JSON.stringify(sorts)
            },
            'GET',
            (data) => callback(data['dragons']['dragon'].map((item) => new Dragon(
                item['id'],
                item['name'],
                item['coordinates']['x'],
                item['coordinates']['y'],
                item['creationDate'],
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
                'name': fields['name'],
                'coordinates': {
                    'x': fields['x'],
                    'y': fields['y']
                },
                'age': fields['age'],
                'color': fields['color'],
                'character': fields['character'],
                'type': fields['type']
            },
            'POST',
            (_) => callback(true)
        )
    }

    updateDragon(id, fields, callback) {
        request_crud(
            'dragons/' + id,
            {
                'name': fields['name'],
                'coordinates': {
                    'x': fields['x'],
                    'y': fields['y']
                },
                'age': fields['age'],
                'color': fields['color'],
                'character': fields['character'],
                'type': fields['type']
            },
            'PUT',
            (_) => callback(true)
        )
    }
}