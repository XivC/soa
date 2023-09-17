import {DragonRepository} from "../../data/repository/dragon_repository.js";
import {PageCollection} from "./page_collection.js";
import {Dragon} from "../../data/model/dragon.js";
import {createFiltersContainer, addFilter} from "../components/filters.js";

export class PageCollectionDragons extends PageCollection {

    dragonRepository = new DragonRepository()
    filters = {}
    onCreate() {
        super.onCreate()
        let container = createFiltersContainer()

        let that = this
        function callback() {
            that.reload()
        }

        addFilter('name', this.filters, container, callback)
        addFilter('id', this.filters, container, callback)
        addFilter('type', this.filters, container, callback)
    }

    fetchElements(page, callback) {
        let items = page > 2 ? [] : [
            new Dragon(1, "Fire Dragon", 10, 20, "2023-09-16", 100, "Red", 1, "Fire", null),
            new Dragon(2, "Ice Dragon", 30, 40, "2023-09-16", 150, "Blue", 2, "Ice", null),
            new Dragon(3, "Earth Dragon", 50, 60, "2023-09-16", 200, "Green", 3, "Earth", null),
        ]
        console.log(this.filters)
        setTimeout(() => callback(items), 500)
        //this.dragonRepository.listDragons(page, callback)
    }

    cardInnerHtml(item) {
        return `
        <h2>${item.name}</h2>
        <p>ID: ${item.id}</p>
        <p>Type: ${item.type}</p>
        <p>Age: ${item.age}</p>
        <p>Color: ${item.color}</p>
        <p>Coordinates: ${item.y}:${item.y}</p>
        <p>Characted ID: ${item.characterId}</p>
        <p>Killer ID: ${item.killerId}</p>
        `;
    }
}
