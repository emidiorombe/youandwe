select (select tgaparam.cgc from tgaparam where rownum=1) as cnpj_transportadora, 
       vea027.codfil  as filial, 
       vea027.numero  as ctrc_numero, 
       decode(vea027.ctrcte,'S',57,8) as tipo, 
       vea027.serie   as ctrc_serie, 
       vea027.dtemis  as ctrc_data, 
       tga017.placa   as placa_frota, 
       tga016.placa   as placa_carreta, 
       vea027.ufcol   as uf_origem, 
       vea027.muncol  as municipio_origem, 
       vea027.ufent   as uf_destino, 
       vea027.munent  as municipio_destino, 
       nvl(vea023.txrctp,0) as taxa_rct, 
       nvl(vea023.txrrp,0)  as taxa_rr, 
       nvl(vea023.txrcfp,0) as taxa_rcf, 
       nvl(vea023.txflup,0) as taxa_fluvial, 
       nvl(vea027.vrmerc,0) as valor_mercadoria, 
       tga015.nome    as nome_motorista, 
       vea027.situac as situacao,  
       vea023.chassi    as veiculo_chassi, 
       vea004.descri    as veiculo_modelo, 
       vea023.nrnfis    as veiculo_numero_nf, 
       vea023.senfis    as veiculo_serie_nf, 
       vea023.dtnfis    as veiculo_data_nf, 
       nvl(vea023.vrnfis,0) as veiculo_valor_nf 
 from   vea027, vea023, vea004, tga011, tga017, tga016, tga015
 where  vea027.codfil = vea023.filemb 
 and    vea027.numero = vea023.nrconh 
 and    vea027.serie  = vea023.serie  
 and    vea023.codmod = vea004.codigo 
 and    vea027.codfil = tga011.codigo 
 and    vea027.codcav = tga017.codigo 
 and    vea027.codcar = tga016.codigo 
 and    vea027.codmot = tga015.codigo 
 and    vea027.datalt between ? and ?