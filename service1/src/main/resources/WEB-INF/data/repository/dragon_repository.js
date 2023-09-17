import {Dragon} from "../model/dragon.js";
import {request_crud} from "../../api/api.js";

export class DragonRepository {

    listDragons(page, filters, sorts, callback) {
        const limit = 5
        console.log(JSON.stringify(filters))
        console.log(JSON.stringify(sorts))
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
}