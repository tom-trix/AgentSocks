/**
 * 
 */
package ru.tomtrix.agentsocks.infrastructure;

/**
 * @author tom-trix
 *
 */
public interface ICodeLoadable
{
	public void loadCode() throws Exception;
	public void compileAgents() throws Exception;
}
