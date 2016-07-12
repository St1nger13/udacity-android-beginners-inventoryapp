package main.inventoryapp.st1nger13.me.inventoryapp;

/**
 * Created by St1nger13 on 29.06.2016.
 */
public class DBContract
{
    /** Data types for columns id DB **/
    public enum DataTypes
    {
        INTEGER("INTEGER") ,
        TEXT("TEXT") ;

        DataTypes(String type)
        {
            this.type = type ;
        }

        @Override
        public String toString()
        {
            return type ;
        }

        private String type ;
    }

    public static class ProductsTable extends Table
    {
        public static final String TABLE_NAME = "all_products" ;
        public static final Column COL_ID = new Column("id", DataTypes.INTEGER.toString()) ;
        public static final Column COL_PRODUCT_NAME = new Column("product_name", DataTypes.TEXT.toString()) ;
        public static final Column COL_QUANTITY = new Column("quantity", DataTypes.INTEGER.toString()) ;
        public static final Column COL_PRICE = new Column("price", DataTypes.INTEGER.toString()) ;
        public static final Column COL_SUPPLIER_MAIL = new Column("supplier_mail", DataTypes.INTEGER.toString()) ;


        public static final String CREATE_TABLE_IF_NOT_EXIST = "CREATE TABLE IF NOT EXISTS "
                + TABLE_NAME + " ("
                + COL_ID.NAME + " " + COL_ID.TYPE + " PRIMARY KEY AUTOINCREMENT, "
                + COL_PRODUCT_NAME.NAME + " " + COL_PRODUCT_NAME.TYPE + ", "
                + COL_QUANTITY.NAME + " " + COL_QUANTITY.TYPE + " DEFAULT 0, "
                + COL_PRICE.NAME + " " + COL_PRICE.TYPE + " DEFAULT 0, "
                + COL_SUPPLIER_MAIL.NAME + " " + COL_SUPPLIER_MAIL.TYPE + " NOT NULL);" ;
    }

    public static abstract class Table
    {
        public static class Column
        {
            public String NAME ;
            public String TYPE ;

            public Column(String colName, String colType)
            {
                this.NAME = colName ;
                this.TYPE = colType ;
            }
        }
    }
}
