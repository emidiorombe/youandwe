select (select tgaparam.cgc from tgaparam where rownum=1) as cnpj_transportadora, --String
       --DADOS DO CTRC
       vea027.codfil  as filial, --Integer
       vea027.numero  as ctrc_numero, --Integer
       decode(vea027.ctrcte,'S',57,8) as tipo, --Integer
       vea027.serie   as ctrc_serie, --String
       vea027.dtemis  as ctrc_data, --Date
       tga017.placa   as placa_frota, --String
       tga016.placa   as placa_carreta, --String
       vea027.ufcol   as uf_origem, --String
       vea027.muncol  as municipio_origem, --String
       vea027.ufent   as uf_destino, --String
       vea027.munent  as municipio_destino, --String
       nvl(vea023.txrctp,0) as taxa_rct, --Double
       nvl(vea023.txrrp,0)  as taxa_rr, --Double
       nvl(vea023.txrcfp,0) as taxa_rcf, --Double
       nvl(vea023.txflup,0) as taxa_fluvial, --Double
       nvl(vea027.vrmerc,0) as valor_mercadoria, --Double
       --CAMPO NOVO
       tga015.nome    as nome_motorista, --String
       vea027.situac as situacao, --String --> Integer (1=Gerado, 2=Emitido, 3=Cancelado)
       --DADOS DO CTRC NAO UTILIZADOS
       --'CTRC'         as tipo_nome, --String
       --(select tgaparam.razao from tgaparam where rownum=1) as nome_emitente, --String
       --tga011.munici  as municipio_emitente, --String
       --tga011.uf      as uf_emitente, --String
       --vea027.datcad  as registro, --Date
       --vea027.codcav  as codigo_frota, --String
       --vea027.codcar  as codigo_carreta, --String
       --tga019.cgc     as cnpj_fabricante, --String
       --tga019.nome    as nome_fabricante, --String
       --tga020.sgcnpj  as cnpj_seguradora, --String
       --tga020.nome    as nome_seguradora, --String
       --vea027.tposeg  as tipo_seguro, --String
       --vea027.tptran  as tipo_transporte, --String
       --DADOS DO VEICULO
       vea023.chassi    as veiculo_chassi, --String
       vea004.descri    as veiculo_modelo, --String
       vea023.nrnfis    as veiculo_numero_nf, --String
       vea023.senfis    as veiculo_serie_nf, --String
       vea023.dtnfis    as veiculo_data_nf, --Date
       nvl(vea023.vrnfis,0) as veiculo_valor_nf --Double
from   vea027, vea023, vea004, tga011, tga017, tga016, tga015
       --, tga034, tga037, tga019, tga020
where  vea027.codfil = vea023.filemb (+)
and    vea027.numero = vea023.nrconh (+)
and    vea027.serie  = vea023.serie  (+)
and    vea023.codmod = vea004.codigo (+)
and    vea027.codfil = tga011.codigo (+)
and    vea027.codcav = tga017.codigo (+)
and    vea027.codcar = tga016.codigo (+)
and    vea027.codmot = tga015.codigo (+)
--and    vea027.codfab = tga019.codigo (+)
--and    vea027.codseg = tga020.codigo (+)
--and    tga034.codfil = vea027.codfil
--and    tga034.numero = vea027.numero
--and    tga034.serie  = vea027.serie
--and    tga034.tpodoc = tga037.codigo (+)
--and    vea027.situac <> '3' -- Cancelado (converter os cancelados)
--and    vea027.coddev = 'FA0001' -- CAOA
--and    vea027.codfab in (10, 27) -- HYUNDAI/SUBARU
and    vea027.datalt between ? and ?

