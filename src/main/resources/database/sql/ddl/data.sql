-- ============================================
-- src/main/resources/data.sql
-- SEED DATA INIZIALE PER TEST
-- ============================================

-- ============================================
-- 1. ADMIN USER (password: admin123)
-- ============================================
INSERT INTO users (email, password, name, surname, role, is_active, created_at, updated_at)
VALUES
('admin@3dprintstore.it', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM3Ux6XOc2xpXi5lMvvC', 'Admin', 'Store', 'ADMIN', true, NOW(), NOW()),
('user@test.it', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM3Ux6XOc2xpXi5lMvvC', 'Mario', 'Rossi', 'USER', true, NOW(), NOW());

-- Password in chiaro per test: admin123
-- Per generare hash BCrypt: https://bcrypt-generator.com/

-- ============================================
-- 2. PRODOTTI STAMPA 3D
-- ============================================
INSERT INTO products (name, description, price, category, dimensions, processing_days, shipping_days, shipping_cost, free_shipping, in_stock, coming_soon, customizable, has_model, model_file_url, featured, bestseller, created_at, updated_at)
VALUES
-- Prodotto 1
('Portachiavi Personalizzato',
 'Portachiavi stampato in 3D completamente personalizzabile con iniziali o testo. Perfetto come regalo o per uso personale. Realizzato con materiali di alta qualità e precisione millimetrica.',
 5.99,
 'STAMPA_3D',
 '5x3x0.5 cm',
 2, 3, 3.00, false, true, false, true, true,
 '/models/portachiavi.stl',
 true, true, NOW(), NOW()),

-- Prodotto 2
('Vaso Geometrico',
 'Elegante vaso con design geometrico moderno. Ideale per piante grasse o come elemento decorativo. Design minimale che si adatta a ogni ambiente.',
 12.99,
 'STAMPA_3D',
 '12x12x15 cm',
 3, 0, 0.00, true, true, false, false, true,
 '/models/vaso-geometrico.stl',
 true, false, NOW(), NOW()),

-- Prodotto 3
('Supporto Cellulare',
 'Supporto per smartphone con angolazione regolabile. Compatibile con tutti i modelli di telefono. Base antiscivolo per massima stabilità.',
 8.99,
 'STAMPA_3D',
 '10x8x5 cm',
 1, 3, 2.00, false, true, false, true, false,
 NULL,
 false, true, NOW(), NOW()),

-- Prodotto 4
('Organizer Scrivania',
 'Organizer multifunzione per scrivania con scomparti per penne, graffette e altri accessori. Design modulare e personalizzabile.',
 15.99,
 'STAMPA_3D',
 '20x10x8 cm',
 4, 0, 0.00, true, true, false, false, true,
 '/models/organizer.stl',
 false, false, NOW(), NOW()),

-- Prodotto 5
('Miniature Gaming',
 'Miniature per giochi da tavolo stampate ad alta risoluzione. Disponibili diversi design fantasy e sci-fi. Perfette per collezionisti.',
 9.99,
 'STAMPA_3D',
 '5x3x3 cm',
 2, 3, 3.00, false, true, false, false, true,
 '/models/miniature.stl',
 true, false, NOW(), NOW()),

-- Prodotto 6
('Decorazione Parete',
 'Pannello decorativo geometrico modulare per parete. Crea composizioni uniche e personalizzate. Facile installazione.',
 18.99,
 'STAMPA_3D',
 '30x30x2 cm',
 3, 4, 4.00, false, true, false, true, false,
 NULL,
 false, false, NOW(), NOW());

-- ============================================
-- 3. PRODOTTI STAMPA LASER
-- ============================================
INSERT INTO products (name, description, price, category, dimensions, processing_days, shipping_days, shipping_cost, free_shipping, in_stock, coming_soon, customizable, has_model, model_file_url, featured, bestseller, created_at, updated_at)
VALUES
-- Prodotto 7
('Incisione su Legno',
 'Targa in legno con incisione laser personalizzata. Ideale per regali, insegne o decorazioni. Legno naturale di alta qualità.',
 14.99,
 'STAMPA_LASER',
 '20x15x0.5 cm',
 2, 3, 3.00, false, true, false, true, false,
 NULL,
 false, true, NOW(), NOW()),

-- Prodotto 8
('Portachiavi Metallo Laser',
 'Portachiavi in metallo con incisione laser. Resistente e durevole. Perfetto per personalizzazioni con loghi o nomi.',
 9.99,
 'STAMPA_LASER',
 '8x3x0.2 cm',
 1, 2, 2.00, false, true, false, true, false,
 NULL,
 false, false, NOW(), NOW()),

-- Prodotto 9
('T-Shirt Personalizzata',
 'Maglietta in cotone con stampa laser personalizzata. Disponibile in varie taglie e colori. Stampa resistente ai lavaggi.',
 19.99,
 'STAMPA_LASER',
 'S/M/L/XL',
 4, 0, 0.00, true, true, false, true, false,
 NULL,
 true, false, NOW(), NOW()),

-- Prodotto 10
('Asciugamano Ricamato',
 'Asciugamano in spugna morbida con ricamo laser personalizzato. Ideale come regalo. Qualità premium.',
 24.99,
 'STAMPA_LASER',
 '50x100 cm',
 3, 4, 4.00, false, true, false, true, false,
 NULL,
 false, false, NOW(), NOW());

-- ============================================
-- 4. ADESIVI
-- ============================================
INSERT INTO products (name, description, price, category, dimensions, processing_days, shipping_days, shipping_cost, free_shipping, in_stock, coming_soon, customizable, has_model, model_file_url, featured, bestseller, created_at, updated_at)
VALUES
-- Prodotto 11
('Adesivo Logo Custom',
 'Adesivo personalizzabile con il tuo logo o design. Materiale resistente alle intemperie. Ideale per laptop, auto, vetrine.',
 2.99,
 'ADESIVI',
 '10x10 cm',
 1, 2, 1.00, false, true, false, true, false,
 NULL,
 true, true, NOW(), NOW()),

-- Prodotto 12
('Sticker Pack Gaming',
 'Set di 10 adesivi a tema gaming. Design esclusivi e colorati. Perfetti per personalizzare setup da gaming.',
 7.99,
 'ADESIVI',
 '5-10 cm cad.',
 1, 0, 0.00, true, true, false, false, false,
 NULL,
 true, false, NOW(), NOW()),

-- Prodotto 13
('Adesivi Decorativi',
 'Set di adesivi decorativi per pareti o mobili. Facile applicazione e rimozione. Design minimalista.',
 4.99,
 'ADESIVI',
 'Vari',
 1, 2, 1.00, false, true, false, false, false,
 NULL,
 false, false, NOW(), NOW()),

-- Prodotto 14
('Etichette Personalizzate',
 'Etichette adesive personalizzate per prodotti, barattoli o organizzazione. Materiale resistente all'acqua.',
 5.99,
 'ADESIVI',
 '5x3 cm',
 2, 2, 2.00, false, true, false, true, false,
 NULL,
 false, true, NOW(), NOW());

-- ============================================
-- 5. STOCK PRODOTTI (Materiali e Colori)
-- ============================================

-- Stock Portachiavi (Prodotto 1)
INSERT INTO product_stock (product_id, material, color, quantity)
VALUES
(1, 'PLA', 'bianco', 50),
(1, 'PLA', 'nero', 30),
(1, 'PLA', 'verde', 20),
(1, 'PETG', 'nero', 15);

-- Stock Vaso (Prodotto 2)
INSERT INTO product_stock (product_id, material, color, quantity)
VALUES
(2, 'PLA', 'verde', 20),
(2, 'PLA', 'bianco', 10);

-- Stock Supporto Cellulare (Prodotto 3)
INSERT INTO product_stock (product_id, material, color, quantity)
VALUES
(3, 'PETG', 'nero', 15),
(3, 'PLA', 'bianco', 12);

-- Stock Organizer (Prodotto 4)
INSERT INTO product_stock (product_id, material, color, quantity)
VALUES
(4, 'PLA', 'bianco', 10),
(4, 'PLA', 'nero', 8);

-- Stock Miniature (Prodotto 5)
INSERT INTO product_stock (product_id, material, color, quantity)
VALUES
(5, 'Resina', 'grigio', 25),
(5, 'Resina', 'bianco', 15);

-- Stock Decorazione (Prodotto 6)
INSERT INTO product_stock (product_id, material, color, quantity)
VALUES
(6, 'PLA', 'verde', 8),
(6, 'PLA', 'bianco', 10);

-- Stock Incisione Legno (Prodotto 7)
INSERT INTO product_stock (product_id, material, color, quantity)
VALUES
(7, 'legno', 'naturale', 30);

-- Stock Portachiavi Metallo (Prodotto 8)
INSERT INTO product_stock (product_id, material, color, quantity)
VALUES
(8, 'metallo', 'acciaio', 50);

-- Stock T-Shirt (Prodotto 9)
INSERT INTO product_stock (product_id, material, color, quantity)
VALUES
(9, 'cotone', 'bianco', 20),
(9, 'cotone', 'nero', 15);

-- Stock Asciugamano (Prodotto 10)
INSERT INTO product_stock (product_id, material, color, quantity)
VALUES
(10, 'spugna', 'bianco', 15),
(10, 'spugna', 'blu', 10);

-- Stock Adesivo Logo (Prodotto 11)
INSERT INTO product_stock (product_id, material, color, quantity)
VALUES
(11, 'vinile', 'bianco', 100),
(11, 'vinile', 'nero', 80),
(11, 'vinile', 'verde', 60);

-- Stock Sticker Pack (Prodotto 12)
INSERT INTO product_stock (product_id, material, color, quantity)
VALUES
(12, 'vinile', 'multicolor', 50);

-- Stock Adesivi Decorativi (Prodotto 13)
INSERT INTO product_stock (product_id, material, color, quantity)
VALUES
(13, 'vinile', 'bianco', 70);

-- Stock Etichette (Prodotto 14)
INSERT INTO product_stock (product_id, material, color, quantity)
VALUES
(14, 'vinile', 'bianco', 90),
(14, 'vinile', 'trasparente', 40);

-- ============================================
-- 6. IMMAGINI PRODOTTI (placeholder)
-- ============================================
INSERT INTO product_images (product_id, image_url, is_primary, display_order)
VALUES
-- Portachiavi
(1, 'products/portachiavi-1.jpg', true, 1),
-- Vaso
(2, 'products/vaso-1.jpg', true, 1),
-- Supporto
(3, 'products/supporto-1.jpg', true, 1),
-- Organizer
(4, 'products/organizer-1.jpg', true, 1),
-- Miniature
(5, 'products/miniature-1.jpg', true, 1),
-- Decorazione
(6, 'products/decorazione-1.jpg', true, 1),
-- Incisione Legno
(7, 'products/legno-1.jpg', true, 1),
-- Portachiavi Metallo
(8, 'products/metallo-1.jpg', true, 1),
-- T-Shirt
(9, 'products/tshirt-1.jpg', true, 1),
-- Asciugamano
(10, 'products/asciugamano-1.jpg', true, 1),
-- Adesivo Logo
(11, 'products/adesivo-1.jpg', true, 1),
-- Sticker Pack
(12, 'products/stickers-1.jpg', true, 1),
-- Adesivi Decorativi
(13, 'products/decorativi-1.jpg', true, 1),
-- Etichette
(14, 'products/etichette-1.jpg', true, 1);

-- ============================================
-- 7. ORDINE DI ESEMPIO (OPZIONALE PER TEST)
-- ============================================
/*
-- Uncomment per creare ordine di test

INSERT INTO orders (user_id, status, total_amount, shipping_cost, customer_name, customer_surname, customer_email, customer_phone, customer_birth_date, shipping_address, shipping_city, shipping_cap, notes, payment_method, created_at, updated_at)
VALUES
(2, 'PENDING', 29.97, 5.00, 'Mario', 'Rossi', 'user@test.it', '+39123456789', '1990-05-15', 'Via Roma 123', 'Napoli', '80100', 'Consegna al citofono', 'credit_card', NOW(), NOW());

-- Items per l'ordine
INSERT INTO order_items (order_id, product_id, quantity, unit_price, selected_material, selected_color, has_customization)
VALUES
(1, 1, 2, 5.99, 'PLA', 'verde', false),
(1, 3, 1, 8.99, 'PETG', 'nero', false);
*/

-- ============================================
-- NOTA:
-- Per generare hash BCrypt delle password:
-- https://bcrypt-generator.com/
-- Oppure usare:
-- String encoded = new BCryptPasswordEncoder().encode("password");
-- ============================================