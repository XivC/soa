import {dragonRepository} from "../../../data/repository/dragon_repository.js";
import {PageCollection} from "../common/page_collection.js";
import {FiltersComponent} from "../../components/filters.js";
import {SortsComponent} from "../../components/sorts.js";
import {PageObjectDragon} from "./page_object_dragon.js";
import {PageObjectPerson} from "../persons/page_object_person.js";
import {PageObjectTeam} from "../teams/page_object_team.js";


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

        document.body.insertAdjacentHTML(
            'afterbegin',
            `<button id="create-person">Create person</button>
                <button id="count-by-type">Count by type</button>
                <button id="sum-age">Sum age</button>`
        )
        document.getElementById("create-person").onclick = () => {
            this.app.pushPage(new PageObjectPerson(this.app, null))
        }
        document.getElementById("count-by-type").onclick = () => {
            let type = prompt("Available values : WATER, UNDERGROUND, AIR, FIRE");
            if (type != null) {
                dragonRepository.countByType(type, (sum, error) => {
                    if (sum) {
                        alert('count-by-type=' + sum)
                    } else {
                        alert(error)
                    }
                })
            }
        }
        document.getElementById("sum-age").onclick = () => {
            dragonRepository.sumAge((sum, error) => {
                if (sum) {
                    alert('sum-age=' + sum)
                } else {
                    alert(error)
                }
            })
        }
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
        let html = `<p>Name: ${item.name}</p>
        <p>ID: ${item.id}</p>
        <p>Type: ${item.type}</p>
        <p>Age: ${item.age}</p>
        <p>Color: ${item.color}</p>
        <p>Coordinates: ${item.x}:${item.y}</p>
        <p>Character: ${item.character}</p>`;
        if (item.killerId !== null) {
            html += `<p>Killer ID: <a href="/?passport-id=${item.killerId}">${item.killerId}</a></p>`;
        }
        return html;
    }

    navigateToItem(item) {
        this.app.pushPage(new PageObjectDragon(this.app, item))
    }
}
