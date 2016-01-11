package org.testfx.playground.view;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Predicate;

import javafx.beans.NamedArg;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * This custom component contains a table (top) and a text field (bottom). The
 * table supports sorting and filtering. Set the items on the filterable table,
 * not on the wrapped table. Set the filter function to decide how elements are
 * filtered.
 *
 * @param <T>
 *            the type of items contained in the table
 */
public class FilterableTableView<T> extends VBox {

	private final TableView<T> tableView;
	private final TextField textField;

	private BiFunction<T, String, Boolean> filterFunction;

	private FilteredList<T> filteredItems;

	public FilterableTableView() {
		this(new TableView<>(), new TextField());
	}

	/**
	 * The FilterableTable requires a TableView child and a TextField child, so
	 * there is no default constructor. If instantiating in FMXL, you should use
	 * the following tags (do not use {@code <children>} tags):
	 *
	 * <pre>
	 * {@code
	 * <FilterableTable>
	 *     <table>...</table>
	 *     <filter>...</filter>
	 * </FilterableTable>
	 * }
	 * </pre>
	 */
	public FilterableTableView(@NamedArg("table") TableView<T> tableView, @NamedArg("filter") TextField textField) {

		this.tableView = tableView;
		this.textField = textField;

		super.getChildren().setAll(tableView, textField);
		super.setVgrow(tableView, Priority.ALWAYS);

		// bindings
		textField.prefWidthProperty().bind(tableView.widthProperty());
		textField.textProperty().addListener((observable, oldFilter, newFilter) -> {
			filteredItems.setPredicate(createPredicate(newFilter));
		});
	}

	private Predicate<T> createPredicate(String filterText) {
		return item -> filterText == null || filterFunction == null || filterFunction.apply(item, filterText);
	}

	/**
	 * This method sets the backing data for the TableView. This should be used
	 * instead of calling {@link TableView#setItems(ObservableList) setItems} on
	 * the wrapped TableView.
	 */
	public void setItems(ObservableList<T> items) {
		Objects.requireNonNull(items, "items");
		if (filterFunction == null) {
			filteredItems = items.filtered(item -> true);
		} else {
			filteredItems = items.filtered(createPredicate(textField.getText()));
		}
		final SortedList<T> sortedItems = filteredItems.sorted();
		sortedItems.comparatorProperty().bind(tableView.comparatorProperty());
		tableView.setItems(sortedItems);
	}

	/**
	 * This function takes an item and the filter text and returns whether the
	 * item should be shown.
	 */
	public void setFilterFunction(BiFunction<T, String, Boolean> filterFunction) {
		this.filterFunction = filterFunction;
	}

}
