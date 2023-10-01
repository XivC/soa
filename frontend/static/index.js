import {PageCollectionDragons} from "./view/pages/dragons/page_collection_dragons.js";
import {App} from './view/app.js'
import {PageObjectPerson} from "./view/pages/persons/page_object_person.js";


class SOAApp extends App {

    constructor() {
        super()
        this.pushPage(new PageCollectionDragons(this))
        this.pushPage(new PageObjectPerson(this, null))
    }
}

new SOAApp()
