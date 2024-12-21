export class Page<T> {
  content: T[];
  pageable: {
    sort: {
      sorted: boolean;
      unsorted: boolean;
      empty: boolean;
    };
    offset: number;
    pageNumber: number;
    pageSize: number;
    unpaged: boolean;
  };
  totalElements: number;
  totalPages: number;
  last: boolean;
  first: boolean;
  number: number;
  size: number;
  empty: boolean;

  constructor() {
    this.content = [];
    this.pageable = {
      sort: {
        sorted: false,
        unsorted: true,
        empty: true,
      },
      offset: 0,
      pageNumber: 1,
      pageSize: 10,
      unpaged: true,
    };
    this.totalElements = 0;
    this.totalPages = 0;
    this.last = false;
    this.first = true;
    this.number = 1;
    this.size = 10;
    this.empty = true;
  }
}
