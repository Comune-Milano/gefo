import { Injectable } from '@angular/core';
import { DecimalPipe, formatNumber } from '@angular/common';
@Injectable({ providedIn: 'root' })
export class ExportTableCsvService {
	exportTableToCsv(filename: string, rows: object[]): void {
		if (!rows || !rows.length) {
			return;
		}
		const separator = ';';
		const keys = Object.keys(rows[0]);
		const csvData =
			'sep=;' + '\n' +
			keys.join(separator) +
			'\n' +
			rows
				.map(row =>
					keys
						.map(k => {
							// let cell = row[k] === null || row[k] === undefined ? '' : row[k];
							/* @ts-ignore */
							let cell = row[k];
							if (typeof cell === 'number') {
								cell = cell.toString().replace('.',',');
							}
							cell = cell instanceof Date ? cell.toLocaleString() : cell.toString().replace(/"/g, '""');
							// cell = typeof cell === 'number' ?  formatNumber(cell,'1.2-2'): cell
							if (cell.search(/("|,|\n)/g) >= 0) {
								cell = `"${cell}"`;
							}
							return cell;
						})
						.join(separator)
				)
				.join('\n');

		const blob = new Blob([csvData], { type: 'text/csv;charset=utf-8;' });
		const link = document.createElement('a');
		if (link.download !== undefined) {
			// Browsers that support HTML5 download attribute
			const url = URL.createObjectURL(blob);
			link.setAttribute('href', url);
			link.setAttribute('download', filename);
			link.style.visibility = 'hidden';
			document.body.appendChild(link);
			link.click();
			document.body.removeChild(link);
		}
	}
}
