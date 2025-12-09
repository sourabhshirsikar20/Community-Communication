-- Clean old data
DELETE FROM logins_roles;
DELETE FROM logins;
DELETE FROM roles;
DELETE FROM communities;
DELETE FROM streets;

-- Insert communities with ALL required fields (Added font_options, font_colour to every insert)

-- Block 1: Corner Shop
insert into communities (
  address, name,
  roof_option, roof_colour,
  base_option, base_colour,
  foreground_option, foreground_colour,
  font_options, font_colour,
  main_text, links_text, info_text, promo_text, approved,
  created_date
) values (
  '01', 'Corner Shop',
  1, '#cc7979',
  1, '#d8c286',
  1, '#f5dfd5ff',
  '{"serif":false,"italic":true,"weight":450,"width":85}', '#e9b36cff',
  'Your local corner shop, here for all everyday items!',
  'www.cornershop.com',
  'Open 7am - 10pm daily',
  'Grand opening sale - 20% off!',
  1,
  NOW()
);

-- Block 2: Sarah Restaurant (Created 1 WEEK AGO - ~175 days left)
insert into communities (
  address, name,
  roof_option, roof_colour,
  base_option, base_colour,
  foreground_option, foreground_colour,
  font_options, font_colour,
  main_text, links_text, info_text, promo_text, approved,
  created_date
) values (
  '0200', 'Sarah Restaurant',
  2, '#be1313ff',
  2, '#ae0fd6ff',
  2, '#4a2d80ff',
  '{"serif":false,"italic":true,"weight":450,"width":85}', '#e9b36cff',
  'A small restaurant with the best food in town!',
  'www.sarahrestaurant.com',
  'Reservations: 555-1234',
  'Try our new dinner menu!',
  1,
  DATE_SUB(NOW(), INTERVAL 1 WEEK)
);

-- Block 3: The Old Library (UNAPPROVED)
insert into communities (
  address, name,
  roof_option, roof_colour,
  base_option, base_colour,
  foreground_option, foreground_colour,
  font_options, font_colour,
  main_text, links_text, info_text, promo_text, approved,
  created_date
) values (
  '03', 'The Old Library',
  1, '#5D4037',
  1, '#F5F5DC',
  1, '#8D6E63',
  '{"serif":true,"italic":false,"weight":400,"width":90,"grade":0}', '#4a3428',
  'A quiet place to study and read books from our vast collection.',
  'www.library.org',
  'Open 9am - 5pm Mon-Sat',
  'Free membership this month!',
  0,
  NULL
);

-- Block 4: Tech Startups Hub (UNAPPROVED)
insert into communities (
  address, name,
  roof_option, roof_colour,
  base_option, base_colour,
  foreground_option, foreground_colour,
  font_options, font_colour,
  main_text, links_text, info_text, promo_text, approved,
  created_date
) values (
  '04', 'Tech Startups Hub',
  1, '#263238',
  2, '#ECEFF1',
  2, '#37474F',
  '{"serif":false,"italic":false,"weight":500,"width":100}', '#1a237e',
  'Co-working space for local tech enthusiasts and startups.',
  'www.techhub.io',
  'Contact: admin@techhub.io',
  'Desks available from $10/day',
  0,
  NULL
);

-- Block 5: Green Gardens (Created 3 MONTHS AGO - ~91 days left - HALFWAY)
insert into communities (
  address, name,
  roof_option, roof_colour,
  base_option, base_colour,
  foreground_option, foreground_colour,
  font_options, font_colour,
  main_text, links_text, info_text, promo_text, approved,
  created_date
) values (
  '05', 'Green Gardens',
  2, '#33691E',
  1, '#8D6E63',
  1, '#558B2F',
  '{"serif":false,"italic":true,"weight":450,"width":85}', '#1b5e20',
  'Fresh vegetables and flowers grown by the community, for the community.',
  'Social: @GreenGardens',
  'Volunteers welcome on weekends',
  'Fresh Carrots available now',
  1,
  DATE_SUB(NOW(), INTERVAL 3 MONTH)
);

-- Block 6: Sweet Treats Bakery (FIXED - added font_options and font_colour)
insert into communities (
  address, name,
  roof_option, roof_colour,
  base_option, base_colour,
  foreground_option, foreground_colour,
  font_options, font_colour,
  main_text, links_text, info_text, promo_text, approved,
  created_date
) values (
  '0201', 'Sweet Treats Bakery',
  2, '#E6B8B7',
  1, '#FFF8E1',
  2, '#D7CCC8',
  '{"serif":false,"italic":true,"weight":450,"width":85}', '#d84315',
  'Freshly baked bread, cakes, and pastries every morning.',
  'www.sweettreats.com',
  'Open 6am - 4pm daily',
  'Half-price croissants after 3pm!',
  1,
  DATE_SUB(NOW(), INTERVAL 3 MONTH)
);

-- Block 7: PowerLift Gym (Created 5 MONTHS, 3 WEEKS AGO - ~7 days left - CRITICAL!)
insert into communities (
  address, name,
  roof_option, roof_colour,
  base_option, base_colour,
  foreground_option, foreground_colour,
  font_options, font_colour,
  main_text, links_text, info_text, promo_text, approved,
  created_date
) values (
  '06', 'PowerLift Gym',
  1, '#424242',
  2, '#B0BEC5',
  1, '#263238',
  '{"serif":false,"italic":true,"weight":450,"width":85}', '#e2ac6f',
  'Your local spot for fitness and strength training.',
  'www.powerlift.local',
  '24/7 Access for members',
  'Join now for $0 initiation fee',
  1,
  DATE_SUB(DATE_SUB(NOW(), INTERVAL 5 MONTH), INTERVAL 21 DAY)
);

-- Block 8: Sunnydale School (UNAPPROVED)
insert into communities (
  address, name,
  roof_option, roof_colour,
  base_option, base_colour,
  foreground_option, foreground_colour,
  font_options, font_colour,
  main_text, links_text, info_text, promo_text, approved,
  created_date
) values (
  '07', 'Sunnydale School',
  2, '#B71C1C',
  2, '#FFCC80',
  2, '#3E2723',
  '{"serif":true,"italic":false,"weight":600,"width":95,"grade":50}', '#5d4037',
  'Educating the next generation of community leaders.',
  'www.sunnydale.edu',
  'Office: 8am - 3:30pm',
  'Enrollment open for Fall 2025',
  0,
  NULL
);

-- Block 9: Paws & Claws Pet Shop (Created EXACTLY 6 MONTHS AGO - Expires TODAY)
insert into communities (
  address, name,
  roof_option, roof_colour,
  base_option, base_colour,
  foreground_option, foreground_colour,
  font_options, font_colour,
  main_text, links_text, info_text, promo_text, approved,
  created_date
) values (
  '08', 'Paws & Claws Pet Shop',
  1, '#0288D1',
  1, '#E1F5FE',
  1, '#0277BD',
  '{"serif":false,"italic":true,"weight":450,"width":85}', '#01579b',
  'Everything you need for your furry friends.',
  'Social: @PawsClawsShop',
  'Grooming services available',
  'Buy 1 get 1 free on dog treats',
  1,
  DATE_ADD(DATE_SUB(NOW(), INTERVAL 6 MONTH), INTERVAL 3 MINUTE)
);

-- Block 10: Classic Bookstore (Created 7 MONTHS AGO - EXPIRED)
insert into communities (
  address, name, 
  roof_option, roof_colour, 
  base_option, base_colour, 
  foreground_option, foreground_colour,
  font_options, font_colour,
  main_text, links_text, info_text, promo_text, approved,
  created_date
) values (
  '09', 'Classic Bookstore',
  1, '#FF0000',
  1, '#CCCCCC',
  1, '#888888',
  '{"serif":true,"italic":false,"weight":400,"width":90,"grade":-20}', '#666666',
  'A cosy bookstore with rare and vintage books for collectors.',
  'www.expired.com',
  'This listing is no longer active',
  'Please contact to renew',
  1,
  DATE_SUB(NOW(), INTERVAL 7 MONTH)
);

-- Block 11: Riverside Community Center (Created 8 MONTHS AGO - EXPIRED)
insert into communities (
  address, name, 
  roof_option, roof_colour, 
  base_option, base_colour, 
  foreground_option, foreground_colour,
  font_options, font_colour,
  main_text, links_text, info_text, promo_text, approved,
  created_date
) values (
  '10', 'Riverside Community Center',
  2, '#8B0000',
  2, '#A9A9A9',
  2, '#696969',
  '{"serif":false,"italic":false,"weight":500,"width":100}', '#444444',
  'A gathering place for community events, classes, and activities.',
  'www.oldcenter.com',
  'Expired listing',
  'No longer active',
  1,
  DATE_SUB(NOW(), INTERVAL 8 MONTH)
);

-- Block 12: Fresh Market (Created 5 MONTHS AGO - ~30 days left - Critical!)
insert into communities (
  address, name, 
  roof_option, roof_colour, 
  base_option, base_colour, 
  foreground_option, foreground_colour,
  font_options, font_colour,
  main_text, links_text, info_text, promo_text, approved,
  created_date
) values (
  '11', 'Fresh Market',
  2, '#4CAF50',
  1, '#E8F5E9',
  2, '#2E7D32',
  '{"serif":false,"italic":true,"weight":450,"width":85}', '#1b5e20',
  'Farm-fresh produce delivered daily.',
  'www.freshmarket.local',
  'Open 7am - 7pm',
  'Organic vegetables 30% off',
  1,
  DATE_SUB(NOW(), INTERVAL 5 MONTH)
);

-- Block 13: Downtown Art Gallery (UNAPPROVED)
insert into communities (
  address, name, 
  roof_option, roof_colour, 
  base_option, base_colour, 
  foreground_option, foreground_colour,
  font_options, font_colour,
  main_text, links_text, info_text, promo_text, approved,
  created_date
) values (
  '12', 'Downtown Art Gallery',
  1, '#9C27B0',
  2, '#F3E5F5',
  1, '#6A1B9A',
  '{"serif":true,"italic":true,"weight":300,"width":85,"grade":100}', '#4a148c',
  'Showcasing local artists and their incredible work.',
  'www.artgallery.local',
  'Tue-Sun 10am - 6pm',
  'New exhibition opening soon!',
  0,
  NULL
);

-- Block 14: The Coffee House (Created 4.5 MONTHS AGO - ~45 days left)
insert into communities (
  address, name, 
  roof_option, roof_colour, 
  base_option, base_colour, 
  foreground_option, foreground_colour,
  font_options, font_colour,
  main_text, links_text, info_text, promo_text, approved,
  created_date
) values (
  '13', 'The Coffee House',
  2, '#5D4037',
  1, '#EFEBE9',
  2, '#3E2723',
  '{"serif":false,"italic":true,"weight":450,"width":85}', '#4e342e',
  'Artisan coffee and cosy atmosphere.',
  'www.coffeehouse.local',
  'Open 6am - 10pm',
  'Latte of the day: $3.50',
  1,
  DATE_SUB(DATE_SUB(NOW(), INTERVAL 4 MONTH), INTERVAL 15 DAY)
);

-- Block 15: Main Street Boutique (Created 1 YEAR AGO - LONG EXPIRED)
insert into communities (
  address, name, 
  roof_option, roof_colour, 
  base_option, base_colour, 
  foreground_option, foreground_colour,
  font_options, font_colour,
  main_text, links_text, info_text, promo_text, approved,
  created_date
) values (
  '14', 'Main Street Boutique',
  1, '#000000',
  1, '#424242',
  1, '#212121',
  '{"serif":false,"italic":false,"weight":600,"width":100}', '#333333',
  'Fashion boutique featuring local designers and unique clothing.',
  'www.abandoned.com',
  'No longer in business',
  'Contact owner',
  1,
  DATE_SUB(NOW(), INTERVAL 12 MONTH)
);

-- SECURITY SETUP

-- Roles
INSERT INTO roles (role_id, name) VALUES (1, 'MANAGER');
INSERT INTO roles (role_id, name) VALUES (2, 'USER');

-- Users
-- Password for 'manager' is: password
INSERT INTO logins (login_name, password, enabled)
VALUES ('manager', '$2a$10$uXkdCPrB3Adlpw0H2EUWWeoMVWcGwtDroMR.YeZ9BW13Za9fe5chu', true);

-- Password for 'user' is: password
INSERT INTO logins (login_name, password, enabled)
VALUES ('user', '$2a$10$1T51cvA5jk4d1uu/P9462.rVKfGXOxLB.olz79MpOpa5XYHHOCblS', true);

-- Role mapping
insert into logins_roles (login_name, role_id) values ('manager', 1);
insert into logins_roles (login_name, role_id) values ('manager', 2);
insert into logins_roles (login_name, role_id) values ('user', 2);

-- Streets

insert into streets (name, address, description) 
values ('Main Street', 'root', 'Everything starts here, explore your community!');

insert into streets (name, address, description) 
values ('New Blocks', '00', 'A place to find newly-added blocks');

insert into streets (name, address, description) 
values ('Food & Drink', '02', 'A home for all restaurants, takeaways, cafes, and more');