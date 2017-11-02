package ro.evozon.tools.utils;

import org.junit.Test;
import org.simpleflatmapper.csv.*;
import org.simpleflatmapper.csv.CsvColumnKey;
import org.simpleflatmapper.csv.CsvWriterBuilder;

import org.simpleflatmapper.map.Mapper;
import org.simpleflatmapper.map.MapperBuilderErrorHandler;
import org.simpleflatmapper.map.property.DateFormatProperty;
import org.simpleflatmapper.map.property.EnumOrdinalFormatProperty;
import org.simpleflatmapper.map.property.FieldMapperColumnDefinition;
import org.simpleflatmapper.map.MapperConfig;
import ro.evozon.tools.ConfigUtils;
import ro.evozon.tools.Constants;
import ro.evozon.tools.models.Location;


import java.io.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.io.File.separator;

public class CsvReaderTest {
    /*private CsvReader createCsvReader() {
        try {
            Path path = Paths.get("src/test/resources", "sample.csv");
            Reader reader = Files.newBufferedReader(
                    path, Charset.forName("UTF-8"));
            return new CsvReader(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }*/

    @Test
    public void readsHeader() {
        System.out.println("name "+ConfigUtils.getOutputFileNameForLocation());
    File file;
        file = new File(Constants.OUTPUT_PATH_DATA_DRIVEN_API+ ConfigUtils.getOutputFileNameForLocation());
        try {
            /*CsvParser
                    .forEach(file, row -> System.out.println(Arrays.toString(row)));*/
            CsvParser
                    .mapTo(Location.class)
                    .stream(
                            file,
                            (s) -> { s.forEach(g-> System.out.println(g.getNumeLocatie())); return null; }
                    );
            // override headers
 /*           CsvMapper<Location> mapper =
                    CsvMapperFactory
                            .newInstance()
                            .defaultDateFormat("yyyyMMdd")
                            .newMapper(Location.class);
            FileReader reader = new FileReader(file);
                CsvParser
                        .mapWith(mapper)
                        .stream(reader)
                        .forEach(k->System.out.println(k.getLocatieId()))*/;



        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void testWriteCsvOnDbObject() throws Exception {
        MapperConfig<CsvColumnKey,FieldMapperColumnDefinition<CsvColumnKey>> config =
                MapperConfig.<CsvColumnKey>fieldMapperConfig();
        CsvMapperBuilder<Location> builderL = new CsvMapperBuilder<Location>(Location.class);
        CsvWriterBuilder<Location> builder = CsvWriterBuilder.newBuilder(Location.class);
        CsvMapper<Location> mapperL = builderL.mapper();
        Mapper<Location, Appendable> mapper =
                builder.addColumn("id")
                        .addColumn("name")
                        .addColumn("email")
                        .addColumn("creation_time", new DateFormatProperty("dd/MM/yyyy HH:mm:ss"))
                        .addColumn("type_ordinal", new EnumOrdinalFormatProperty())
                        .addColumn("type_name")
                        .mapper();
        Location locationObj = new Location();
        locationObj.setLocatieId("1234");
/*        List<Location> list = mapperL.forEach(l->)*/
        System.out.println("??"+mapper.map(locationObj).toString());
        System.out.println("??"+mapper.map(locationObj).toString());
    }


    }
