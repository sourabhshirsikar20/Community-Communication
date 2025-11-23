delete from communities;

insert into communities (
  address, name, 
  roof_option, roof_colour, 
  base_option, base_colour, 
  foreground_option, foreground_colour,
  main_text, links_text, info_text, promo_text
  ) values (
    '00', 'Corner Shop',
    0, '#cc7979',
    0, '#d8c286',
    0, '#f5dfd5ff',
    'Your local corner shop, here for all everyday items! ...',
    '-links text-',
    '-info text-',
    '-promo text-'
  );

  insert into communities (
  address, name, 
  roof_option, roof_colour, 
  base_option, base_colour, 
  foreground_option, foreground_colour,
  main_text, links_text, info_text, promo_text
  ) values (
    '01', 'Sarah Resturant',
    0, '#be1313ff',
    0, '#3f3417ff',
    0, '#80472dff',
    'A small Restraunt with the best food in town!',
    '-links text-',
    '-info text-',
    '-promo text-'
  );


  insert into block (Address, Test) values ('00','Test 1');
  insert into block (Address, Test) values ('01','Test 2');
  insert into block (Address, Test) values ('02','Test 3');
  insert into block (Address, Test) values ('0200','Test 3.1');
  insert into block (Address, Test) values ('03','Test 4');