import * as API from "../../api";


export class DragonRepository {

    api = new API.CRUDApi()

    listDragons(page, callback) {
        this.api.dragonsGet(API.DragonType.AIR, (error, data, response) => {
            if (error) {
                console.error(error);
            } else {
                console.log('API called successfully. Returned data: ' + data);
                callback(data)
            }
        });
        // Initialize a list of Dragon instances
        // setTimeout(() => callback(page >= 3 ? [] : [
        //     new Dragon(1, "Fire Dragon", 10, 20, "2023-09-16", 100, "Red", 1, "Fire", 4),
        //     new Dragon(2, "Ice Dragon", 30, 40, "2023-09-16", 150, "Blue", 2, "Ice", 5),
        //     new Dragon(3, "Earth Dragon", 50, 60, "2023-09-16", 200, "Green", 3, "Earth", 6),
        // ]), 500);
    }
}