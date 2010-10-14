'''
Created on Oct 14, 2010

@author: Rafael Nunes
'''
DEFAULT_PAGING = 25

def get_paging(pg_number):
    init = (pg_number - 1) * DEFAULT_PAGING
    end = pg_number * DEFAULT_PAGING
    page = pg_number
        
    return init, end, page      
