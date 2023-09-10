create-conf:
	cp ctl/db.properties.keep ctl/db.properties

create-conf-win:
	copy .\ctl\db.properties.keep .\ctl\db.properties

conf-win:
	copy .\ctl\db.properties .\service1\main\resources\db.properties

conf:
	cp ctl/db.properties service1/main/resources/db.properties

migrate:
	cd ctl && python migrate.py