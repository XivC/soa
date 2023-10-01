import {dragonRepository} from "../../../data/repository/dragon_repository.js";
import {PageCollection} from "../common/page_collection.js";
import {FiltersComponent} from "../../components/filters.js";
import {SortsComponent} from "../../components/sorts.js";
import {PageObjectDragon} from "./page_object_dragon.js";


export class PageCollectionDragons extends PageCollection {

    constructor(app) {
        super(app);
    }

    onCreate() {
        super.onCreate()
        let fields = [
            "id",
            "name",
            "coordinate_x",
            "coordinate_y",
            "creation_date",
            "age",
            "color",
            "type",
            "character",
            "killer_id"
        ]
        this.filtersComponent = new FiltersComponent(
            fields,
            () => this.reload()
        )

        this.sortsComponent = new SortsComponent(
            fields,
            () => this.reload()
        )
    }

    fetchElements(page, callback) {
        dragonRepository.listDragons(
            page,
            this.filtersComponent.getFilters(),
            this.sortsComponent.getSorts(),
            callback
        )
    }

    cardInnerHtml(item) {
        return `
        <p>Name: ${item.name}</p>
        <p>ID: ${item.id}</p>
        <p>Type: ${item.type}</p>
        <p>Age: ${item.age}</p>
        <p>Color: ${item.color}</p>
        <p>Coordinates: ${item.x}:${item.y}</p>
        <p>Character: ${item.character}</p>
        <p>Killer ID: ${item.killerId}</p>
        `;
    }

    navigateToItem(item) {
        this.app.pushPage(new PageObjectDragon(this.app, item))
    }
}
