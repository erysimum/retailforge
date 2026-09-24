TRUNCATE TABLE products RESTART IDENTITY;

INSERT INTO products (sku, name, description, price) VALUES
                                                         ('RF-BED-001', 'Classic Cotton Sheet Set', '300 thread count cotton sheet set in crisp white', 89.00),
                                                         ('RF-BED-002', 'Washed Linen Sheet Set', '100% linen sheet set with a relaxed natural finish', 249.00),
                                                         ('RF-BED-003', 'Bamboo Comfort Sheet Set', 'Soft and breathable bamboo blend sheet set', 159.00),
                                                         ('RF-BED-004', 'Premium Sateen Sheet Set', '600 thread count cotton sateen sheet set', 199.00),
                                                         ('RF-PILLOW-001', 'Classic Cotton Pillowcases', 'Pair of 300 thread count cotton pillowcases', 39.00),
                                                         ('RF-PILLOW-002', 'Linen Pillowcases', 'Pair of relaxed washed linen pillowcases', 69.00),
                                                         ('RF-BED-005', 'Textured Quilt Cover', 'Soft textured cotton quilt cover in neutral tones', 129.00),
                                                         ('RF-BATH-001', 'Egyptian Cotton Bath Towel', 'Premium heavyweight cotton bath towel', 49.00),
                                                         ('RF-BATH-002', 'Plush Hand Towel', 'Soft everyday hand towel', 24.00),
                                                         ('RF-HOME-001', 'Woven Cotton Throw', 'Decorative woven cotton throw for bedroom or living room', 99.00);
