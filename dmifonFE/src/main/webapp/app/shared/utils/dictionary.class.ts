export class Dictionary {
  items: any;
  constructor() {
    this.items = {};
  }

  public has(key: string): any {
    return key in this.items;
  }
  public set(key: string, value: string): void {
    this.items[key] = value;
  }
  public get(key: string): any {
    return this.items[key];
  }
  public delete(key: string): boolean {
    if (this.has(key)) {
      delete this.items[key];
      return true;
    }
    return false;
  }
}
