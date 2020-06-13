package mhandharbeni.eparking.database.migrations;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class Migrations {
    public static final Migration MIGRATION_0_1 = new Migration(0, 1) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

        }
    };

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

        }
    };

    public static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

        }
    };

    public static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

        }
    };

    public static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

        }
    };

    public static final Migration MIGRATION_5_6 = new Migration(5, 6) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

        }
    };

    public static final Migration MIGRATION_6_7 = new Migration(6, 7) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

        }
    };

    public static final Migration MIGRATION_7_8 = new Migration(7, 8) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

        }
    };

    public static final Migration MIGRATION_8_9 = new Migration(8, 9) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

        }
    };

    public static final Migration MIGRATION_9_10 = new Migration(9, 10) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

        }
    };

    public static final Migration MIGRATION_10_11 = new Migration(10, 11) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL(
                    "CREATE TABLE ResponseDataHistory " +
                            "(" +
                                "id INTEGER, " +
                                "buyer TEXT, " +
                                "storeId INTEGER, " +
                                "totalPrice INTEGER, " +
                                "tax INTEGER," +
                                "service INTEGER," +
                                "discount INTEGER," +
                                "status TEXT," +
                                "trxBy INTEGER," +
                                "dateCreated TEXT," +
                                "PRIMARY KEY(id)" +
                            ")"
            );
        }
    };

    public static final Migration MIGRATION_11_12 = new Migration(11, 12) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL(
                    "ALTER TABLE ResponseDataMaterial ADD name TEXT");
        }
    };

    public static final Migration MIGRATION_12_13 = new Migration(12, 13) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL(
                    "CREATE TABLE ResponseDataSupplier " +
                            "(" +
                                "id INTEGER," +
                                "name TEXT," +
                                "createdBy INTEGER," +
                                "modifiedBy INTEGER," +
                                "dateCreated TEXT," +
                                "dateModified TEXT," +
                                "storeId INTEGER," +
                                "PRIMARY KEY(id)" +
                            ")"
            );
        }
    };

    public static final Migration MIGRATION_13_14 = new Migration(13, 14) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL(
                    "CREATE TABLE ResponseDataVoucher " +
                            "(" +
                            "id INTEGER," +
                            "type_voucher INTEGER," +
                            "id_store INTEGER," +
                            "value INTEGER," +
                            "percentage TEXT," +
                            "item INTEGER," +
                            "list_item TEXT," +
                            "date_expired TEXT," +
                            "max_used INTEGER," +
                            "nama_voucher TEXT," +
                            "used INTEGER," +
                            "isActive INTEGER," +
                            "PRIMARY KEY(id)" +
                            ")"
            );
        }
    };

    public static final Migration MIGRATION_14_15 = new Migration(14, 15) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL(
                    "ALTER TABLE ResponseDataProduct ADD category_id TEXT");
        }
    };

    public static final Migration MIGRATION_15_16 = new Migration(15, 16) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL(
                    "CREATE TABLE ResponseDataCategory " +
                            "(" +
                            "id INTEGER," +
                            "nama TEXT," +
                            "storeId INTEGER," +
                            "PRIMARY KEY(id)" +
                            ")"
            );
        }
    };

}
