SET FOREIGN_KEY_CHECKS = 0;
DELETE FROM table_sample;
DELETE FROM city;
DELETE FROM country;
DELETE FROM secondary_table_sample;
DELETE FROM entity_attributes;
DELETE FROM id_class_sample;
DELETE FROM simple_id_sample;
DELETE FROM embedded_id_sample;
DELETE FROM embedded_entity_owner;
DELETE FROM collection_list_slave;
DELETE FROM collection_map_slave;
DELETE FROM collection_owner;
DELETE FROM entity_two;
DELETE FROM entity_one;
DELETE FROM otm_ud_one;
DELETE FROM otm_ud_many;
DELETE FROM otm_ud_one_many;
DELETE FROM one_to_many_join_table;
DELETE FROM mtm_join_table;
DELETE FROM mtm_bd_left;
DELETE FROM mtm_bd_right;
DELETE FROM location_order_by;
DELETE FROM company_order_by;
DELETE FROM item;
DELETE FROM joined_item;
DELETE FROM joined_book;
DELETE FROM joined_newspaper;
DELETE FROM tpc_book;
DELETE FROM tpc_item;
DELETE FROM tpc_newspaper;
DELETE FROM extender_mapped_super_class;
DELETE FROM orphan_two;
DELETE FROM orphan_one;
SET FOREIGN_KEY_CHECKS = 1;