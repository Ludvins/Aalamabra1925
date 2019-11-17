package com.example.aaalamabra1925

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

/*
This class uses SQLite API to connect with a database where all the interest points are stored.
 */
class DbManager(context: Context) {

    private val dbName = "Alamabra1925"
    private val dbTable = "InterestPoint"
    private val colId = "Id"
    private val colTitle = "Title"
    private val colContent = "Content"
    private val colLocationType = "LocationType"
    private val colLat = "Latitude"
    private val colLong = "Longitude"
    private val dbVersion = 1

    private val MODOALHAMBRA = false

    private val createTableSql =
            "CREATE TABLE IF NOT EXISTS $dbTable ($colId INTEGER PRIMARY KEY, " +
                    "$colTitle TEXT, " +
                    "$colContent TEXT, " +
                    "$colLocationType INTEGER, " +
                    "$colLat REAL, " +
                    "$colLong REAL);"
    private var db: SQLiteDatabase? = null

    init {
        val dbHelper = DatabaseHelper(context)
        db = dbHelper.writableDatabase
    }

    // Inserts a given Interest Point from a 'ContentValues' object
    private fun insert(values: ContentValues): Long {
        return db!!.insert(dbTable, "", values)
    }
    // Returns an iterable cursor over the interest point with the given id.
    fun queryById(id: Int): Cursor {
        return db!!.rawQuery("select * from $dbTable where $colId = $id", null)
    }
    // Returns an iterable cursor over all interest points.
    fun queryAll(): Cursor {
        return db!!.rawQuery("select * from $dbTable", null)
    }
    // Returns an iterable cursor over all elements with the given location type.
    fun queryByLocationType(type: Int): Cursor {
        return db!!.rawQuery("select * from $dbTable where $colLocationType = $type", null)
    }

    // Deletes entries in the database. Unused in this project.
    private fun delete(selection: String, selectionArgs: Array<String>): Int {
        return db!!.delete(dbTable, selection, selectionArgs)
    }

    // Clears the database. Used for debugging.
    fun deleteAll(): Int {
        return db!!.delete(dbTable, null, null)
    }

    // Updates values in the database. Unused in this project.
    fun update(values: ContentValues, selection: String, selectionargs: Array<String>): Int {
        return db!!.update(dbTable, values, selection, selectionargs)
    }

    // Fills the database with the given interest points

    fun fillDatabase(): Long{
        var id : Long? = null
        if(MODOALHAMBRA){
            id = insert(createCV(1, "Alcazaba", "La Alcazaba de la Alhambra que junto a las Torres Bermejas, es la parte más antigua del recinto monumental, fue dedicada a la vigilancia y control de la ciudad, data del siglo IX. Del mismo modo, el rey estableció en esta parte la residencia habitual de su ejército de élite.\n" + "\n" + "No sólo servía para la defensa contra los enemigos sino también contra sublevaciones internas. Esta gran muralla defensiva permitía que, incluso con la caída de la ciudad protegida, la ciudadela resistiera durante un largo periodo.\n\n Dentro de este recinto podemos encontrar las torres de la Quebrada, la del Homenaje y la famosa torre de la Vela.",
                    0, 37.176970, -3.592357))
            insert(createCV(2, "Palacios Nazaríes", "Residencia habitual de los reyes de Granada, su construcción comenzó a principios del siglo XIV. Los Palacios Nazaríes son un conjunto palacial compuesto por tres edificaciones:\n\n- El Mexuar, es la sala más antigua. Usada para reuniones de los ministros del rey así como lugar donde se impartía justicia.\n\n -A continuación llegamos al palacio de Comares, su construcción data de la época de Yusuf I, esta residencia fue constituida entorno al patio de los Arrayanes, de tal forma que en los laterales puedes encontrar las salas de los Embajadores y la de la Barca. La sala de los Embajadores se encuentra dentro de la torre de Comares.\n\n- El palacio de los Leones, de la época de Mohamed V, son también estancias reales. Está compuesto por un patio central, el Patio de los Leones, y en cada lateral una sala,  la de los Mocárabes, los Reyes, Dos Hermanas, Ajimeces, mirador de Daraxa, los Abencerrajes y el Harén.",
                    0,37.177345, -3.589746))
            insert(createCV(3, "Palacio de Carlos V", "La iniciativa para la construcción del palacio partió del emperador Carlos a partir de su boda con Isabel de Portugal, celebrada en Sevilla en 1526. Tras el enlace, la pareja residió varios meses en la Alhambra, quedando profundamente impresionado por los Palacios nazaríes, dejando encargada la construcción del nuevo palacio con la intención de establecer su residencia en la Alhambra granadina",
                    0, 37.176775, -3.589881))
            insert(createCV(4, "Puerta del vino", "La Puerta del Vino sirve de inicio y final en el itinerario de visita a la Alcazaba. En cierto modo esta Puerta, mantiene una función semejante a la que tuvo en la época nazarí. Es la Puerta principal de acceso a la Medina de la Alhambra, la que encierra, dentro del común recinto amurallado de la fortaleza, el sector residencial y artesano al servicio de la corte.\n\nAl ser puerta interior su acceso es directo, a diferencia de las puertas exteriores que debían estar más protegidas y eran construidas en recodo. No obstante, en su ámbito interior conserva el espacio necesario y los bancos para la guardia que controlaba el paso.",
                    0, 37.176660, -3.590710))
            insert(createCV(5, "Iglesia de Santa Maria de la Alhambra", "En el conjunto monumental de la Alhambra, entre el Palacio de Carlos V y los Baños árabes de la Alhambra, se encuentra la Iglesia de Santa María de la Encarnación de la Alhambra, conocida por todos, simplemente, como Santa María de la Alhambra. \n Como muchos templos cristianos levantados tras la conquista castellana, la iglesia de Santa María de la Alhambra, fue también erigida sobre una antigua mezquita. En este caso sobre la Mezquita Real de la Alhambra, que según Ibn al-Jatib, fue construida en tiempos de Muhammad III (1302-1309). Esta práctica de eregir templos religiosos en sustitución de otros, ha sido habitual a lo largo de la historia. De hecho, la Mezquita Real de la Alhambra, se levantó sobre un templo cristiano anterior, de época visigoda. ",
                    0, 37.176394, -3.589148))
            insert(createCV(6, "Placeta de los Aljibes", "Antes de la conquista cristiana, donde ahora se encuentra la plaza de los Aljibes, había una gran barranquera que separaba el recinto militar (la Alcazaba), de la zona residencial. \n\nMendoza, conde de Tendilla decidió allanar el terreno, y construir un gran aljibe, que aseguraba el suministro de agua tanto del recinto palatino como de gran parte de la ciudad. \n\nEl aljibe que centra la plaza cuenta con unas impresionantes dimensiones, está considerado como uno de los mayores de su época. Es de planta rectangular, mide 34 metros de largo por 6 de ancho, por 8 de alto, y es capaz de albergar más de 1630 metros cúbicos de agua, 5 veces más que el aljibe del Rey.",
                    0, 37.177078, -3.590916))
            insert(createCV(7, "Puerta de la justicia", "La Puerta de la Justicia se inauguró, según una inscripción en la lápida fundacional, restaurada recientemente, en el año 1348, en tiempos de Yussuf I. Siglo y medio más tarde, tras la rendición de Boabdil ante los Reyes Católicos, se esculpió sobre esta lápida una figura de la Virgen y el Niño, obra de Roberto Alemán.\n\nTras atravesar la entrada, a la salida de la puerta, aparece otro símbolo cristiano: un retablo de Diego Navas, erigido en 1588 a petición de los cristianos de la zona. Pero en el trayecto entre la entrada y la salida de la puerta permanece la decoración original nazarí, como los rombos cerámicos del arco de herradura o la cúpula pintada de rojo imitando ladrillo; incluso la madera y la obra en hierro de las propias puertas son las originales.",
                    0,37.176116, -3.590279))
            insert(createCV(8, "Pilar de Carlos V", "Junto a la Puerta de la Justicia puede observarse un baluarte circular de artillería desde el que desciende un muro en piedra labrada, ante el que se talló una de las obras maestras del Renacimiento granadino, el Pilar de Carlos V, con dos cuerpos de altura y composición tripartita centrada en torno a tres mascarones surtidores. Estos son interpretados por algunos como símbolos de los ríos de Granada: Darro, Beiro y Genil, y otros como el Verano, Primavera y Otoño por tener sus cabezas coronadas con espigas, flores y frutas.\n\nEn el centro del segundo cuerpo existe una cartela con inscripción alusiva al Emperador Carlos V, flanqueado por pilastras que acogen las armas de Borgoña y Lorena con las columnas de Hércules. Se remata con un ático de medio punto en cuyo tímpano está esculpido el escudo imperial.",
                    0,37.175882, -3.590086))
            insert(createCV(9, "Puerta de los Carros", "La puerta de los Carros no es original de época Nazarí, sino que fue realizada con posterioridad, entre los años 1526 y 1536, una apertura en el lienzo de la muralla a causa de la necesidad por las obras que se realizaban para la construcción del Palacio de Carlos V. En el proyecto original estaba planteado que delante de sus fachadas se emplazaría una gran Plaza de Armas porticada con sus correspondientes placetas adyacentes. Aunque el proyecto quedó inconcluso, se llegó a nivelar el terreno colindante al palacio y hoy día queda reflejada la intención original en su nombre \"las placetas\".\nEsta puerta no solo servía de acceso peatonal, ya que como su nombre indica se usaba para los carros que accedían al conjunto palaciego. Actualmente es el acceso principal a la zona baja del Conjunto Monumental, dando acceso a distintos servicios y dependencias de la Alhambra. ",
                    0,37.175905, -3.589293))

            insert(createCV(10, "Museo de Carlos V", "Aquí podrás encontrar varios objetos de la época así como su historia y curiosidades.",
                    2,900.0 ,1900.0))
            insert(createCV(11, "Mazmorras", "En estas mazmorras encerraba Carlos V a sus enemigos.",
                    2,900.0 ,200.0))
            insert(createCV(12, "Escaleras", "Por estas escaleras subia Carlos V hacia la torre.",
                    2,300.0 ,200.0))
        }else{
            id = insert(createCV(1, "Alcazaba", "La Alcazaba de la Alhambra que junto a las Torres Bermejas, es la parte más antigua del recinto monumental, fue dedicada a la vigilancia y control de la ciudad, data del siglo IX. Del mismo modo, el rey estableció en esta parte la residencia habitual de su ejército de élite.\n" + "\n" + "No sólo servía para la defensa contra los enemigos sino también contra sublevaciones internas. Esta gran muralla defensiva permitía que, incluso con la caída de la ciudad protegida, la ciudadela resistiera durante un largo periodo.\n\n Dentro de este recinto podemos encontrar las torres de la Quebrada, la del Homenaje y la famosa torre de la Vela.",
                    0, 37.185634, -3.611182))
            insert(createCV(2, "Palacios Nazaríes", "Residencia habitual de los reyes de Granada, su construcción comenzó a principios del siglo XIV. Los Palacios Nazaríes son un conjunto palacial compuesto por tres edificaciones:\n\n- El Mexuar, es la sala más antigua. Usada para reuniones de los ministros del rey así como lugar donde se impartía justicia.\n\n -A continuación llegamos al palacio de Comares, su construcción data de la época de Yusuf I, esta residencia fue constituida entorno al patio de los Arrayanes, de tal forma que en los laterales puedes encontrar las salas de los Embajadores y la de la Barca. La sala de los Embajadores se encuentra dentro de la torre de Comares.\n\n- El palacio de los Leones, de la época de Mohamed V, son también estancias reales. Está compuesto por un patio central, el Patio de los Leones, y en cada lateral una sala,  la de los Mocárabes, los Reyes, Dos Hermanas, Ajimeces, mirador de Daraxa, los Abencerrajes y el Harén.",
                    0,37.185511, -3.610471))
            insert(createCV(3, "Palacio de Carlos V", "La iniciativa para la construcción del palacio partió del emperador Carlos a partir de su boda con Isabel de Portugal, celebrada en Sevilla en 1526. Tras el enlace, la pareja residió varios meses en la Alhambra, quedando profundamente impresionado por los Palacios nazaríes, dejando encargada la construcción del nuevo palacio con la intención de establecer su residencia en la Alhambra granadina",
                    0, 37.176775, -3.589881))
            insert(createCV(4, "Puerta del vino", "La Puerta del Vino sirve de inicio y final en el itinerario de visita a la Alcazaba. En cierto modo esta Puerta, mantiene una función semejante a la que tuvo en la época nazarí. Es la Puerta principal de acceso a la Medina de la Alhambra, la que encierra, dentro del común recinto amurallado de la fortaleza, el sector residencial y artesano al servicio de la corte.\n\nAl ser puerta interior su acceso es directo, a diferencia de las puertas exteriores que debían estar más protegidas y eran construidas en recodo. No obstante, en su ámbito interior conserva el espacio necesario y los bancos para la guardia que controlaba el paso.",
                    0, 37.176660, -3.590710))
            insert(createCV(5, "Iglesia de Santa Maria de la Alhambra", "En el conjunto monumental de la Alhambra, entre el Palacio de Carlos V y los Baños árabes de la Alhambra, se encuentra la Iglesia de Santa María de la Encarnación de la Alhambra, conocida por todos, simplemente, como Santa María de la Alhambra. \n Como muchos templos cristianos levantados tras la conquista castellana, la iglesia de Santa María de la Alhambra, fue también erigida sobre una antigua mezquita. En este caso sobre la Mezquita Real de la Alhambra, que según Ibn al-Jatib, fue construida en tiempos de Muhammad III (1302-1309). Esta práctica de eregir templos religiosos en sustitución de otros, ha sido habitual a lo largo de la historia. De hecho, la Mezquita Real de la Alhambra, se levantó sobre un templo cristiano anterior, de época visigoda. ",
                    0, 37.176394, -3.589148))
            insert(createCV(6, "Placeta de los Aljibes", "Antes de la conquista cristiana, donde ahora se encuentra la plaza de los Aljibes, había una gran barranquera que separaba el recinto militar (la Alcazaba), de la zona residencial. \n\nMendoza, conde de Tendilla decidió allanar el terreno, y construir un gran aljibe, que aseguraba el suministro de agua tanto del recinto palatino como de gran parte de la ciudad. \n\nEl aljibe que centra la plaza cuenta con unas impresionantes dimensiones, está considerado como uno de los mayores de su época. Es de planta rectangular, mide 34 metros de largo por 6 de ancho, por 8 de alto, y es capaz de albergar más de 1630 metros cúbicos de agua, 5 veces más que el aljibe del Rey.",
                    0, 37.177078, -3.590916))
            insert(createCV(7, "Puerta de la justicia", "La Puerta de la Justicia se inauguró, según una inscripción en la lápida fundacional, restaurada recientemente, en el año 1348, en tiempos de Yussuf I. Siglo y medio más tarde, tras la rendición de Boabdil ante los Reyes Católicos, se esculpió sobre esta lápida una figura de la Virgen y el Niño, obra de Roberto Alemán.\n\nTras atravesar la entrada, a la salida de la puerta, aparece otro símbolo cristiano: un retablo de Diego Navas, erigido en 1588 a petición de los cristianos de la zona. Pero en el trayecto entre la entrada y la salida de la puerta permanece la decoración original nazarí, como los rombos cerámicos del arco de herradura o la cúpula pintada de rojo imitando ladrillo; incluso la madera y la obra en hierro de las propias puertas son las originales.",
                    0,37.176116, -3.590279))
            insert(createCV(8, "Pilar de Carlos V", "Junto a la Puerta de la Justicia puede observarse un baluarte circular de artillería desde el que desciende un muro en piedra labrada, ante el que se talló una de las obras maestras del Renacimiento granadino, el Pilar de Carlos V, con dos cuerpos de altura y composición tripartita centrada en torno a tres mascarones surtidores. Estos son interpretados por algunos como símbolos de los ríos de Granada: Darro, Beiro y Genil, y otros como el Verano, Primavera y Otoño por tener sus cabezas coronadas con espigas, flores y frutas.\n\nEn el centro del segundo cuerpo existe una cartela con inscripción alusiva al Emperador Carlos V, flanqueado por pilastras que acogen las armas de Borgoña y Lorena con las columnas de Hércules. Se remata con un ático de medio punto en cuyo tímpano está esculpido el escudo imperial.",
                    0,37.175882, -3.590086))
            insert(createCV(9, "Puerta de los Carros", "La puerta de los Carros no es original de época Nazarí, sino que fue realizada con posterioridad, entre los años 1526 y 1536, una apertura en el lienzo de la muralla a causa de la necesidad por las obras que se realizaban para la construcción del Palacio de Carlos V. En el proyecto original estaba planteado que delante de sus fachadas se emplazaría una gran Plaza de Armas porticada con sus correspondientes placetas adyacentes. Aunque el proyecto quedó inconcluso, se llegó a nivelar el terreno colindante al palacio y hoy día queda reflejada la intención original en su nombre \"las placetas\".\nEsta puerta no solo servía de acceso peatonal, ya que como su nombre indica se usaba para los carros que accedían al conjunto palaciego. Actualmente es el acceso principal a la zona baja del Conjunto Monumental, dando acceso a distintos servicios y dependencias de la Alhambra. ",
                    0,37.175905, -3.589293))

            insert(createCV(10, "Museo de Carlos V", "Aquí podrás encontrar varios objetos de la época así como su historia y curiosidades.",
                    2,900.0 ,1900.0))
            insert(createCV(11, "Mazmorras", "En estas mazmorras encerraba Carlos V a sus enemigos.",
                    2,900.0 ,200.0))
            insert(createCV(12, "Escaleras", "Por estas escaleras subia Carlos V hacia la torre.",
                    2,300.0 ,200.0))
        }


        return id
    }

    // Creates a contentValues with the given data from an interest points.
    private fun createCV(id: Int, title: String, content: String, locationType: Int, latitude: Double, longitude: Double): ContentValues {
        val aux = ContentValues()
        aux.put("Id", id)
        aux.put("Title", title)
        aux.put("Content", content)
        aux.put("LocationType", locationType)
        aux.put("Latitude", latitude)
        aux.put("Longitude", longitude)
        return aux
    }

    // Database helper class used to handle database creation and updates.
    inner class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, dbName, null, dbVersion) {

        private var context: Context? = context

        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(createTableSql)
            Toast.makeText(this.context, " database is created", Toast.LENGTH_LONG).show()
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("Drop table IF EXISTS $dbTable")
            Toast.makeText(this.context, "Dropped table $dbTable", Toast.LENGTH_LONG).show()
        }
    }
}