create-conf:
	cp ctl/db.properties.keep ctl/db.properties
	cp ctl/haproxy.cfg.keep ctl/haproxy.cfg

create-conf-win:
	copy .\ctl\db.properties.keep .\ctl\db.properties

conf-win:
	copy .\ctl\db.properties .\service1\src\main\resources\db.properties

conf:
	cp ctl/db.properties killer/src/main/resources/db.properties
	cp ctl/db.properties rest/src/main/resources/db.properties

migrate:
	cd ctl && python3 migrate.py