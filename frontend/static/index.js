import {PageCollectionDragons} from "./view/pages/dragons/page_collection_dragons.js";
import {App} from './view/app.js'
import {PageObjectPerson} from "./view/pages/persons/page_object_person.js";
import {personRepository} from "./data/repository/person_repository.js";
import {dragonRepository} from "./data/repository/dragon_repository.js";
import {PageObjectDragon} from "./view/pages/dragons/page_object_dragon.js";


class SOAApp extends App {

    constructor() {
        super()

        const queryString = window.location.search
        const urlParams = new URLSearchParams(queryString)
        if (urlParams.has('passport-id')) {
            const passportID = urlParams.get('passport-id')
            personRepository.getPerson(passportID, (entity, error) => {
                if (entity) {
                    this.pushPage(new PageObjectPerson(this, entity))
                } else {
                    alert(error)
                }
            })
        }
        if (urlParams.has('dragon-id')) {
            const dragonID = urlParams.get('dragon-id')
            dragonRepository.getDragon(dragonID, (entity, error) => {
                if (entity) {
                    this.pushPage(new PageObjectDragon(this, entity))
                } else {
                    alert(error)
                }
            })
        }

        this.pushPage(new PageCollectionDragons(this))
    }
}

new SOAApp()
