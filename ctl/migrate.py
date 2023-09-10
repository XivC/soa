import psycopg2
from jproperties import Properties
import glob

db_props = Properties()

with open('db.properties', 'rb') as props:
    db_props.load(props)


def exec_migration(cursor, file):
    statements = file.read().split(';')
    for statement in statements:
        if statement:
            cursor.execute(statement)


def migrate():
    connection = psycopg2.connect(db_props.get('db.url').data)
    try:
        migrations_files = glob.glob('migrations/*.sql')
        cursor = connection.cursor()
        for file_name in migrations_files:
            with open(file_name) as file:
                exec_migration(cursor, file)
                connection.commit()
        cursor.close()
    finally:
        connection.close()


if __name__ == '__main__':
    migrate()
