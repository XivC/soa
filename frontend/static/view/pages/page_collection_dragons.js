import {DragonRepository} from "../../data/repository/dragon_repository.js";
import {PageCollection} from "./page_collection.js";
import {FiltersComponent} from "../components/filters.js";
import {SortsComponent} from "../components/sorts.js";
import {PageObjectDragon} from "./page_object_dragon.js";

export class PageCollectionDragons extends PageCollection {

    dragonRepository = new DragonRepository()

    constructor(app) {
        super(app);
    }

    onCreate() {
        super.onCreate()
        this.filtersComponent = new FiltersComponent(
            ['id', 'name', 'character'],
            () => this.reload()
        )

        this.sortsComponent = new SortsComponent(
            ['id', 'name', 'character'],
            () => this.reload()
        )
    }

    fetchElements(page, callback) {
        this.dragonRepository.listDragons(
            page,
            this.filtersComponent.getFilters(),
            this.sortsComponent.getSorts(),
            callback
        )
    }

    cardInnerHtml(item) {
        return `
        <h2>${item.name}</h2>
        <p>ID: ${item.id}</p>
        <p>Type: ${item.type}</p>
        <p>Age: ${item.age}</p>
        <p>Color: ${item.color}</p>
        <p>Coordinates: ${item.y}:${item.y}</p>
        <p>Character: ${item.character}</p>
        <p>Killer ID: ${item.killerId}</p>
        `;
    }

    navigateToItem(item) {
        this.app.pushPage(new PageObjectDragon(this.app, item))
    }
}
